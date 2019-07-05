package com.ivanalvarado.architecture_components.ui.user_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ivanalvarado.architecture_components.repository.UserRepositoryImpl
import com.ivanalvarado.architecture_components.repository.models.UserDetailModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class UserDetailViewModel @AssistedInject constructor(
    private val userRepository: UserRepositoryImpl,
    @Assisted private val userId: Int
) : ViewModel() {

    private val TAG = UserDetailViewModel::class.java.simpleName

    private val disposables = CompositeDisposable()

    private val reloadTrigger = MutableLiveData<Boolean>()
    private val _userDetailStream = MutableLiveData<UserDetailModel>()
    private val userDetail: LiveData<UserDetailModel> = Transformations.switchMap(reloadTrigger) {
        userRepository.getUserDetail(userId.toString(), reloadTrigger.value!!)
    }

    val userDetailStream: LiveData<UserDetailModel> get() = _userDetailStream

    init {
//        refreshUserDetail()
    }

    fun getUserDetail(): LiveData<UserDetailModel> = userDetail

    fun refreshUserDetail(forceRefresh: Boolean = false) {
        reloadTrigger.value = forceRefresh
    }

    fun getUserDetailStream(forceRefresh: Boolean) {
        disposables.add(
            userRepository.getUserDetailRx(userId.toString(), forceRefresh)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({ userDetail ->
                    _userDetailStream.postValue(userDetail)
                }, { error ->
                    Timber.e(error.localizedMessage)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(userId: Int): UserDetailViewModel
    }
}
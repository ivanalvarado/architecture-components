package com.ivanalvarado.architecture_components

import androidx.lifecycle.LiveData
import com.ivanalvarado.architecture_components.database.dao.UserDao
import com.ivanalvarado.architecture_components.database.dao.UserDetailDao
import com.ivanalvarado.architecture_components.database.entity.UserEntity
import com.ivanalvarado.architecture_components.network.StackOverflowSyncer
import com.ivanalvarado.architecture_components.repository.UserRepository
import com.ivanalvarado.architecture_components.repository.UserRepositoryImpl
import com.ivanalvarado.architecture_components.repository.models.UserDetailModel
import com.ivanalvarado.architecture_components.repository.models.UserModel
import com.ivanalvarado.architecture_components.ui.user_list.User
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.quality.Strictness

class UserRepositoryTest {

    private lateinit var userRepository: UserRepository

    val mockitoStrictness: Strictness get() = Strictness.LENIENT
    @Rule
    fun mockitoRule(): MockitoRule = MockitoJUnit.rule().strictness(mockitoStrictness)

    @Mock
    lateinit var userDao: UserDao

    @Mock
    lateinit var userDetailDao: UserDetailDao

    @Mock
    lateinit var stackOverflowSyncer: StackOverflowSyncer

    @Before
    fun setup() {
        userRepository = UserRepositoryImpl(userDao, userDetailDao, stackOverflowSyncer)
    }

    @Test
    fun getUsersRx() {
        `when`(userDao.getUsersStreamRx()).thenReturn(
            Single.just(
                listOf(
                    UserEntity(
                        id = 123,
                        userName = "Test1",
                        reputation = 1000,
                        imageUrl = "fake.image.url.com",
                        websiteUrl = "fake.image.url.com",
                        lastAccessDate = 0
                    ),
                    UserEntity(
                        id = 456,
                        userName = "Test2",
                        reputation = 2000,
                        imageUrl = "fake.image.url.com",
                        websiteUrl = "fake.image.url.com",
                        lastAccessDate = 1
                    )
                )
            )
        )

        val subscriber = TestObserver<List<User>>()

        userRepository.getUsersRx().subscribe(subscriber)

        subscriber.assertValueCount(1)
        subscriber.assertValues(listOf(
            User(
                userId = 123,
                userName = "Test1",
                imageUrl = "fake.image.url.com"
            ),
            User(
                userId = 456,
                userName = "Test2",
                imageUrl = "fake.image.url.com"
            )
        ))
    }

}

class UserRepositoryImplTest(userDao: UserDao) : UserRepository {
    override fun getUsers(): LiveData<List<UserModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUsersRx(): Single<List<User>> {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserDetail(userId: String, forceRefresh: Boolean): LiveData<UserDetailModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
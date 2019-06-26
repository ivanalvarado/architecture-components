package com.ivanalvarado.architecture_components

import com.ivanalvarado.architecture_components.database.dao.UserDao
import com.ivanalvarado.architecture_components.database.dao.UserDetailDao
import com.ivanalvarado.architecture_components.database.entity.UserEntity
import com.ivanalvarado.architecture_components.network.StackOverflowSyncer
import com.ivanalvarado.architecture_components.repository.UserRepositoryImpl
import com.ivanalvarado.architecture_components.ui.user_list.User
import com.ivanalvarado.architecture_components.util.MockitoTest
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

class UserRepositoryTest : MockitoTest() {

    private lateinit var userRepository: UserRepositoryImpl

    @Mock
    lateinit var userDao: UserDao

    @Mock
    lateinit var userDetailDao: UserDetailDao

    @Mock
    lateinit var stackOverflowSyncer: StackOverflowSyncer

    private val userEntities = listOf(
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

    @Before
    fun setup() {
        userRepository = UserRepositoryImpl(userDao, userDetailDao, stackOverflowSyncer)
    }

    @Test
    fun getUsersRx() {
        `when`(userDao.getUsersStreamRx()).thenReturn(Single.just(userEntities))

        val subscriber = TestObserver<List<User>>()

        userRepository.getUsersRx().subscribe(subscriber)

        subscriber.assertValueCount(1)
        subscriber.assertValues(
            listOf(
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
            )
        )
    }

}
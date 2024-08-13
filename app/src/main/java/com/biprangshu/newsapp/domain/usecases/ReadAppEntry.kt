package com.biprangshu.newsapp.domain.usecases

import com.biprangshu.newsapp.domain.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(): Flow<Boolean> {
        return localUserManager.ReadAppEntry()
    }

}
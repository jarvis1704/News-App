package com.biprangshu.newsapp.domain.usecases

import com.biprangshu.newsapp.domain.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
){
    suspend operator fun invoke(){
        localUserManager.SaveAppEntry()
    }

}
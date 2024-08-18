package com.biprangshu.newsapp.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.newsapp.domain.model.Article
import com.biprangshu.newsapp.domain.usecases.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    var sideEffect by mutableStateOf<String?>(null)
        private set

    fun onEvent(event: DetailsEvent){
        when(event){
            is DetailsEvent.UpsertDeleteArticle ->{
                viewModelScope.launch {
                    val article = newsUseCases.selectArticle(event.article.url)
                    if(article==null){
                        upsertArticle(event.article)
                    }else{
                        deleteArticle(event.article)
                    }
                }
            }

            is DetailsEvent.RemoveSideEffect ->{
                sideEffect=null
            }
        }

    }

    private suspend fun deleteArticle(article: Article) {
        newsUseCases.deleteArticle(article)
        sideEffect="Article Deleted"
    }

    private suspend fun upsertArticle(article: Article) {
        newsUseCases.upsertArticle(article)
        sideEffect="Article Saved"
    }


}
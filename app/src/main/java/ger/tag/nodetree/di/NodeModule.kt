package ger.tag.nodetree.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ger.tag.nodetree.data.data_base.AppDatabase
import ger.tag.nodetree.data.data_base.NodeDao
import ger.tag.nodetree.data.repository.NodeRepositoryImpl
import ger.tag.nodetree.model.repository.NodeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NodeModule {

    @Binds
    fun bindNodeRepository(impl: NodeRepositoryImpl): NodeRepository

    companion object{

        @Singleton
        @Provides
        fun provideNodeDao(@ApplicationContext context: Context): NodeDao {
            return AppDatabase.getInstance(context).getNodeDao()
        }
    }
}
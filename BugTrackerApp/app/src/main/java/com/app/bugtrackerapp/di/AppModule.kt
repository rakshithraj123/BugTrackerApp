package com.app.bugtrackerapp.di

import com.app.bugtrackerapp.data.Repository
import com.app.bugtrackerapp.data.RepositoryImpl
import com.app.bugtrackerapp.data.remote.NetworkRepository
import com.app.bugtrackerapp.data.remote.NetworkRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent


@Module
//@InstallIn(SingletonComponent::class)
@InstallIn(value = [SingletonComponent::class, FragmentComponent::class])
abstract class AppModule  {

    @Binds
    abstract fun bindRepository( repository: RepositoryImpl): Repository

    @Binds
    abstract fun bindNetworkRepository( repository: NetworkRepositoryImpl): NetworkRepository

    //@Binds
    //abstract fun bindLocalRepository( repository: LocalRepositoryImpl): LocalRepository
}

//ActivityComponent ,FragmentComponent,ViewComponent/ViewWithFragmentComponent,ServiceComponent,ApplicationComponent

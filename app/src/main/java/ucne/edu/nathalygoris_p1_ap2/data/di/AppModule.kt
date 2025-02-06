package ucne.edu.nathalygoris_p1_ap2.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ucne.edu.nathalygoris_p1_ap2.data.local.database.SistemaDb
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideSistemaDb(@ApplicationContext applicationContext: Context): SistemaDb =
        Room.databaseBuilder(
            applicationContext,
            SistemaDb::class.java,
            "Parcial.db"
        ).fallbackToDestructiveMigration()
            .build()
    @Provides
    @Singleton
    fun provideSistemaDao(appDataDb: SistemaDb) = appDataDb.sistemaDao()
}
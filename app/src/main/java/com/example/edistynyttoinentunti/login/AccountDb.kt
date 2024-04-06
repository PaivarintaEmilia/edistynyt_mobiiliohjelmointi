package com.example.edistynyttoinentunti.login

// LUODAAN ROOM-DATABASE LOGINIA VARTEN

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

// Roomi käyttää Entittiya, saatiin kaptissa
// Entity on taulu
@Entity("account")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val accessToken: String
)


// Minkälaisia kyselyitä voidaan tehdä databaseen määritetään Daossa
@Dao
abstract class AccountDao {

    // Eka annotaatio millä insertataan dataa
    @Insert
    abstract suspend fun addToken(entity: AccountEntity)

    // Toinen annotaatio millä haetaan viimeiseksi luotu accessToken
    @Query("SELECT accessToken FROM account ORDER BY id DESC LIMIT 1;")
    abstract suspend fun getToken() : String?
}

// Lisätään database
@Database(entities = [AccountEntity::class], version = 1)
abstract class AccountDatabase : RoomDatabase() {
    abstract fun accountDao() : AccountDao
}
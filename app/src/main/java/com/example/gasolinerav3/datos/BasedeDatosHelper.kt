package com.example.gasolinerav3.datos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatosHelper(context: Context) :
    SQLiteOpenHelper(context, "gasolinera.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val crearTipoCombustible = """
            CREATE TABLE IF NOT EXISTS TipoCombustible (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT UNIQUE NOT NULL
            );
        """.trimIndent()

        val crearEstacion = """
            CREATE TABLE IF NOT EXISTS Estacion (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                direccion TEXT,
                latitud REAL NOT NULL,
                longitud REAL NOT NULL
            );
        """.trimIndent()

        val crearBomba = """
            CREATE TABLE IF NOT EXISTS Bomba (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                estacionId INTEGER NOT NULL,
                tipoId INTEGER NOT NULL,
                cantidad INTEGER NOT NULL,
                FOREIGN KEY (estacionId) REFERENCES Estacion(id),
                FOREIGN KEY (tipoId) REFERENCES TipoCombustible(id)
            );
        """.trimIndent()

        val crearStockCombustible = """
            CREATE TABLE IF NOT EXISTS StockCombustible (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                estacionId INTEGER NOT NULL,
                tipoId INTEGER NOT NULL,
                litrosDisponibles REAL NOT NULL,
                fechaActualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (estacionId) REFERENCES Estacion(id),
                FOREIGN KEY (tipoId) REFERENCES TipoCombustible(id)
            );
        """.trimIndent()

        val crearFila = """
            CREATE TABLE IF NOT EXISTS Fila (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                estacionId INTEGER NOT NULL,
                tipoId INTEGER NOT NULL,
                tiempoEstimado INTEGER NOT NULL,
                alcanzaCombustible BOOLEAN NOT NULL,
                fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (estacionId) REFERENCES Estacion(id),
                FOREIGN KEY (tipoId) REFERENCES TipoCombustible(id)
            );
        """.trimIndent()

        // Ejecutar todas las tablas
        db.execSQL(crearTipoCombustible)
        db.execSQL(crearEstacion)
        db.execSQL(crearBomba)
        db.execSQL(crearStockCombustible)
        db.execSQL(crearFila)
 // Tipos de combustible
        db.execSQL("INSERT INTO TipoCombustible (nombre) VALUES ('Gasolina');")
        db.execSQL("INSERT INTO TipoCombustible (nombre) VALUES ('Diesel');")

        // Estaciones
        db.execSQL("INSERT INTO Estacion (nombre, direccion, latitud, longitud) VALUES ('Berea', 'Calle 1', -17.79976, -63.18077);")
        db.execSQL("INSERT INTO Estacion (nombre, direccion, latitud, longitud) VALUES ('Alemana', 'Calle 2', -17.76784, -63.17067);")
        db.execSQL("INSERT INTO Estacion (nombre, direccion, latitud, longitud) VALUES ('Estación Pirai', 'Av Roque Aguilera', -17.78593, -63.20438);")

        // Bombas
        db.execSQL("INSERT INTO Bomba (estacionId, tipoId, cantidad) VALUES (1, 1, 6);") // Berea - Gasolina
        db.execSQL("INSERT INTO Bomba (estacionId, tipoId, cantidad) VALUES (2, 2, 3);") // Alemana - Diesel
        db.execSQL("INSERT INTO Bomba (estacionId, tipoId, cantidad) VALUES (3, 1, 2);") // Pirai - Gasolina
        db.execSQL("INSERT INTO Bomba (estacionId, tipoId, cantidad) VALUES (3, 2, 4);") // Pirai - Diesel


    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Fila")
        db.execSQL("DROP TABLE IF EXISTS StockCombustible")
        db.execSQL("DROP TABLE IF EXISTS Bomba")
        db.execSQL("DROP TABLE IF EXISTS Estacion")
        db.execSQL("DROP TABLE IF EXISTS TipoCombustible")
        onCreate(db)
    }
}

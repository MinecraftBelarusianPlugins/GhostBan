package by.siarhiejbahdaniec.ghostban.config


interface ConfigHolder {
    fun getString(key: String): String
    fun getDouble(key: String): Double
    fun reloadConfigFromDisk()
}

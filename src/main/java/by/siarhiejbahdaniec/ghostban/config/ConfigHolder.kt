package by.siarhiejbahdaniec.ghostban.config


interface ConfigHolder {
    fun getString(key: String?): String
    fun getString(key: String?, def: String?): String
    fun getStringList(key: String?): List<String?>
    fun getInt(key: String?): Int
    fun getBoolean(key: String?): Boolean
    fun reloadConfigFromDisk()
}

package hzh.my_go_web.util.game

import hzh.my_go_web.model.Color
import java.io.File
import java.io.InputStreamReader
import java.io.OutputStreamWriter

//class PVEGame {
//    private val process: Process
//    private val inputStreamReader: InputStreamReader
//    private val outputStreamWriter: OutputStreamWriter
//    private val errorStreamReader: InputStreamReader
//
//    init {
//        val scriptPath = "start.sh"
//        val command = "zsh $scriptPath"
//        val workingDirectory = "/Users/h/Downloads/PhoenixGo"
//        process = Runtime.getRuntime().exec(command, null, File(workingDirectory))
//        inputStreamReader = InputStreamReader(process.inputStream, "UTF-8")
//        errorStreamReader = InputStreamReader(process.errorStream, "UTF-8")
//        outputStreamWriter = OutputStreamWriter(process.outputStream, "UTF-8")
//        initGame()
//    }
//
//    suspend fun activeInputChannel() {
//        coroutineScope {
//            launch {
//                var ch: Char? = null
//                var step = StringBuilder()
//                while ({ ch = inputStreamReader.read().toChar(); ch }() != null) {
//                    if (ch != '=' && ch != ' ' && ch != '\n') {
//                        step.append(ch)
//                    }
//                    if (ch == '\n' && step.isNotEmpty()) {
//                        inputChannel.send(step.toString())
//                        step.clear()
//                    }
//                }
//            }
//        }
//    }
//
//    suspend fun waitForNextStep() = inputChannel.receive()
//
//    // Go Text Protocol
//    private fun gtpCommand(command: String) {
//        outputStreamWriter.write("$command\n")
//        outputStreamWriter.flush()
//    }
//
//    private fun initGame() {
//        gtpCommand("boardsize 19")
//        clearBoard()
//    }
//
//    fun clearBoard() {
//        gtpCommand("clear_board")
//    }
//
//    fun quit() {
//        gtpCommand("quit")
//    }
//
//    fun playerMove(color: Color, location: String) {
//        gtpCommand("play ${color.value} $location")
//    }
//
//    fun aiMove(color: Color) {
//        gtpCommand("genmove ${color.value}")
//    }
//}
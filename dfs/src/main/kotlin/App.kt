package agents

import jade.core.Profile
import jade.core.ProfileImpl
import jade.core.Runtime
import java.lang.Exception
import kotlin.random.Random
import kotlin.system.exitProcess


const val AGENTS_COUNT = 10
const val LEADER_ID = "1"

enum class MessageType {
    Request, Response
}


fun main() {
    val runtime = Runtime.instance()
    val profile = ProfileImpl()
    profile.setParameter(Profile.MAIN_HOST, "localhost")
    profile.setParameter(Profile.MAIN_PORT, "10098")
//    profile.setParameter(Profile.GUI, "true")
    val mainContainer = runtime.createMainContainer(profile)
    val agents = (1..AGENTS_COUNT).map { i ->
        mainContainer.createNewAgent(i.toString(), "agents.DefaultAgent", null)
    }
    agents.forEach { agent ->
        agent.start()
    }
}

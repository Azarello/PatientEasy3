package furhatos.app.patienteasy3

import furhatos.app.patienteasy3.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class Patienteasy3Skill : Skill() {
    override fun start() {
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}

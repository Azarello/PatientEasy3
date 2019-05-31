package furhatos.app.patienteasy3.flow

import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import java.util.*
import furhatos.app.patienteasy3.nlu.*



val Start : State = state(Interaction) {
    onEntry {
        furhat.say("Welcome to this module, in which you will learn about the third part of establishing a conscious " +
                " therapeutic alliance. This involves getting the patient to declare a specific instance in which their issue " +
                " manifests in their life. In order to get a better understanding of what the patient is struggling " +
                " with, it is helpful to have a concrete example of how their issue affects their life.")
        furhat.say(" By having a particular instance when their issue manifests, the therapist can gain greater " +
                " insight into the root of the problem, as well as finding ways of dealing with it.")
        furhat.ask(" In order to to complete the module, you will have to block five defenses successfully, getting  " +
                " the patient to declare a specific instance of the problem. If you are ready say continue," +
                " if you would like to hear the instructions again say repeat.")
    }

    onResponse<Continue> {
        goto(DeclareSpecific)
    }

    onResponse<Repeat> {
        goto(StartGoto)
    }
}

val StartGoto : State = state {
    onEntry {
        goto(Start)
    }
}


val Wait3 : State = state {

    onTime(delay=1500) {
        furhat.ask("When you are ready for the next defense, say next")
    }

    onResponse<Continue> {
        goto(Counter3)
    }
}


val DeclareSpecific : State = state {

    val rand = Random()
    val num = rand.nextInt(5)

    onEntry {
        when (num) {
            0 -> goto(Generalization3)
            1 -> goto(NoMemory3)
            2 -> goto(Diversification3)
            3 -> goto(Avoidance3)
            4 -> goto(Intellectualization3)
        }
    }
}



var counter3 = 0

val Counter3 : State = state {

    onEntry {
        counter3 += 1
        if (counter3 < 5)
            goto(DeclareSpecific)
        else {
            furhat.say(" There was a talk last weekend when I got angry with my dad right after he mentioned my " +
                    "mother's illness.")
            goto(Resolution3)
        }
    }
}



val Generalization3Goto : State = state {
    onEntry {
        goto(Generalization3)
    }
}



val Generalization3 : State = state {

    val rand = Random()
    val num = rand.nextInt(5)

    onEntry {
        when (num) {
            0 -> furhat.ask(" It's the same pattern every time we talk on the phone")
            1 -> furhat.ask(" I just feel overall frustrated whenever it happens and don't know what to do")
            2 -> furhat.ask(" It's just this sense of annoyance and it keeps coming up in many instances")
            3 -> furhat.ask(" I can't give a specific example because it's a bigger issue, I think it's got " +
                    "to do with my overall character")
            4 -> furhat.ask(" I have this tendency in those situations to flare up instead of reacting calmly")
        }
    }

    onResponse<GeneralizationBlock3> {

        it.intent.feel
        it.intent.avoid
        it.intent.person
        it.intent.general
        it.intent.notice
        it.intent.specific
        it.intent.problem
        it.intent.specific

        when (num) {
            0 -> furhat.say(" Yes that's correct. Notice how the patient is referring to an overall 'pattern' rather," +
                    " than specifying a particular instance in which this pattern took place. ")
            1 -> furhat.say(" Perfect. The patient does mention their feelings of frustration, which is good. " +
                    " However, they talk about the feeling of frustration in a general sense rather than describing an " +
                    " event that made them frustrated ")
            2 -> furhat.say(" Great job, the patient is indeed being general. The phrase 'in many instances'  is " +
                    " very telling that the patient is talking about a general trend rather than a specific situation")
            3 -> furhat.say(" That's right. Another way patients can be general is by talking about their overall " +
                    "personality or character instead of sharing a specific example when their problem showed up")
            4 -> furhat.say(" Yes, this is generalization. Notice how the patient offers some valuable insight " +
                    " about their situation, but none the less fails to re-tell a particular scenario, in which they " +
                    " experienced emotional difficulty. ")
        }

        goto(Counter3)
    }



    onResponse<NoMemoryBlock3> {
        furhat.say(" Not quite. Lack of memory defenses are generally more explicit in that patients directly state " +
                " they cannot remember. This is not really the case here, try again in the next defense of the same kind.")
        goto(Generalization3Goto)
    }

    onResponse<DiversificationBlock3> {
        furhat.say(" Good guess. It certainly seems like the patient is trying to change the topic in order to avoid " +
                " providing a specific example. They are still, however, talking about the issue at large. See if you can" +
                " spot the correct defense in this next example of the same category. ")
        goto(Generalization3Goto)
    }

    onResponse<AvoidanceBlock3> {
        furhat.say(" Not really. In avoidance, the patient is rather aware of their strategy and mention it explicitly." +
                " In this case the patient is doing something a bit more subtle, try to catch in the coming defense of " +
                " a similar kind")
        goto(Generalization3Goto)
    }

    onResponse<IntellecutalizationBlock3> {
        furhat.say(" Good idea, but not quite right. It is true the patient is distancing themselves by " +
                " thinking about larger patterns rather than specifics. But this kind of distancing is a particular " +
                " defense mechanism, see if you can remember it in relation to this next defense.")
        goto(Generalization3Goto)
    }

    onResponse<TryAgain> {
        furhat.say(" Ok, let's try another defense. ")
        goto(Wait3)
    }

    onResponse<GiveAnswer> {
        furhat.say(" That was generalization. Notice how the patient is presenting a large or general account of the" +
                " issue, using words like 'pattern', 'usually', or 'overall', instead of providing a specific instance of " +
                " their problem")
        goto(Wait3)
    }

    onResponse<Hint> {
        furhat.say(" Is the patient being specific about their response? Notice how instead of giving a particular " +
                " instance, they turn it into something larger. What defense mechanism follows this pattern?")
        goto(Generalization3Goto)
    }

}

val NoMemory3Goto : State = state {
    onEntry {
        goto(NoMemory3)
    }
}


val NoMemory3 : State = state {

    val rand = Random()
    val num = rand.nextInt(5)

    onEntry {
        when (num) {
            0 -> furhat.ask(" I can't really remember a particular time it happened")
            1 -> furhat.ask(" How am I supposed to remember all the details that happen in my life")
            2 -> furhat.ask(" Thinking about it I can't really recall a specific episode when I got angry")
            3 -> furhat.ask(" I just have a vague sense of what happened, it's not very clear in my memory")
            4 -> furhat.ask(" I just know it happened sometime last weekend but can not remember more than that")
        }
    }

    onResponse<NoMemoryBlock3> {

        it.intent.general
        it.intent.avoid
        it.intent.memory
        it.intent.notice
        it.intent.problem
        it.intent.specific

        when (num) {
            0 -> furhat.say(" That's right. This is a rather explicit example of no memory since the patient clearly" +
                    " states they cannot remember a particular instance of what is being asked about")
            1 -> furhat.say(" Perfect. Even though the patient is not explicitly stating they cannot remember, they " +
                    " are still hiding behind a poor memory excuse to avoid answering the question directly. ")
            2 -> furhat.say(" Great job. A rather evident example of no memory considering the wording, 'I can't really" +
                    " recall'. One way to deal with this is to encourage the patient to see if, after all, they can't " +
                    " remember something important. ")
            3 -> furhat.say(" Exactly so. It may be tempting to accept no memory defenses since it is possible for" +
                    " people to simply forget. It is, however, rather unlikely that patients have no memory of such " +
                    " emotionally salient events. ")
            4 -> furhat.say(" That's correct. When dealing with no memory defenses it is wise to keep in mind that the" +
                    " patient may genuinely not remember certain details. However, if they consistently 'cannot " +
                    " remember' significant events, it may be a sign they are using it as a defense mechanism.")
        }

        goto(Counter3)
    }


    onResponse<GeneralizationBlock3> {
        furhat.say(" Not quite. Generalization implies the patient is giving large and non-specific information. In " +
                " this case the patient is also rather vague, but notice why specifically they are not providing a more " +
                " detailed response. Try this in the next similar response")
        goto(NoMemory3Goto)
    }

    onResponse<DiversificationBlock3> {
        furhat.say(" Not really. The patient is perhaps slightly changing the topic, but really there is a more " +
                " direct reason why they are not sharing a specific example of their problem. See if you can spot it " +
                " in the next defense of the same kind.")
        goto(NoMemory3Goto)
    }

    onResponse<AvoidanceBlock3> {
        furhat.say(" Good guess, but not quite right. The patient is indeed rather clear about why they are not" +
                " describing a specific scenario, and seem to be aware of it. However, there is a specific reason they" +
                " are unable to provide you with the information, which gives you a clue as to what defense mechanism" +
                " it is. ")
        goto(NoMemory3Goto)
    }

    onResponse<IntellecutalizationBlock3> {
        furhat.say(" Not so. In intellectualization, patients offer thoughts instead of feelings. In this defense, the" +
                " patient does not provide much information at all. Why is that the case? ")
        goto(NoMemory3Goto)
    }

    onResponse<TryAgain> {
        furhat.say("Ok, let's try another defense")
        goto(Wait3)
    }

    onResponse<GiveAnswer> {
        furhat.say(" This is a case of a no memory response. Notice how the patient avoids describing an instance " +
                "of their problem by saying they cannot remember one way or another. Of course patients may genuinely " +
                " not remember certain things, but if they consistently cannot recall emotionally important events, it" +
                " may be a hint they are using it as a defense mechanism.")
        goto(DeclareSpecific)
    }

    onResponse<Hint> {
        furhat.say(" What excuse is the patient using to not describe a particular instance of their problem? If " +
                " you listen closely, the patient is being rather direct about why they fail to provide you with this information.")
        goto(NoMemory3Goto)
    }
}

val Diversification3Goto : State = state {

    onEntry {
        goto(Diversification3)
    }
}

val Diversification3 : State = state {

    val rand = Random()
    val num = rand.nextInt(5)

    onEntry {
        when (num) {
            0 -> furhat.ask(" Another thing that is bothering me is how the rest of the family backs up my dad")
            1 -> furhat.ask(" I just want to mention first how crazy the mall was yesterday with all those sales")
            2 -> furhat.ask(" Did I tell you about the traffic on my way here. That's why I was a bit late")
            3 -> furhat.ask(" Truth be told my dad is overall a good guy. This one time he really helped me out " +
                    "when I was in financial trouble")
            4 -> furhat.ask(" You make me feel like I do when my wife complains I don't do the dishes. Though " +
                    "she does have a point sometimes.")
        }
    }

    onResponse<DiversificationBlock3> {

        when (num) {
            0 -> furhat.say(" That's correct. Especially when the patient is changing the topic to something seemingly " +
                    " related, as in this case, it is important to not get off track and immediately block the defense.")
            1 -> furhat.say( "This is indeed diversification. Notice how the patient brings in a completely unrelated " +
                    " topic in order to avoid sharing a specific example")
            2 -> furhat.say(" Great job. In diversification, the patient attempts to avoid their feelings by " +
                    " changing the topic. Block this by refocusing attention on the issue, at hand and avoid being " +
                    " dragged into the story. ")
            3 -> furhat.say(" Perfect. The patient is avoiding describing a specific instance of the problem by " +
                    " telling an unrelated story about their father. It is fine to acknowledge this, but make sure to " +
                    " get the process back on track without lingering on diverse topics.")
            4 -> furhat.say(" That's right. Whenever the patient talks about something seemingly unrelated, such " +
                    " as his wife and dishes, this is a good clue he is using diversification as a defense mechanism.")
        }

        goto(Counter3)

    }

    onResponse<GeneralizationBlock3> {
        furhat.say(" Good guess. Generalization implies the patient is talking about the issue at hand, just that he is  " +
                " not being specific enough. Notice in the next defense if the patient is still talking about the same " +
                " topic at all.")
        goto(Diversification3Goto)
    }

    onResponse<NoMemoryBlock3> {
        furhat.say(" Not quite. No memory defense suggests the person is seemingly struggling with remembering " +
                " a specific instance. This was not the case in this defense, see if you can spot the correct one in " +
                " the next defense of the same kind")
    }

    onResponse<AvoidanceBlock3> {
        furhat.say(" Very good guess. The patient is certainly avoiding the topic by not providing a specific example" +
                " of their problem. In avoidance, however, the patient is consciously aware of avoiding their emotions. " +
                " In the next defense, in what way is the patient avoiding the therapeutic process?")
    }

    onResponse<IntellecutalizationBlock3> {
        furhat.say(" Nice try. It is true the patient is mentioning thoughts in a way, but what are the thoughts" +
                " about? Are they related to the issue at hand?")
        goto(Diversification3Goto)
    }

    onResponse<TryAgain> {
        furhat.say(" Ok, let's try another defense")
        goto(Wait3)
    }

    onResponse<GiveAnswer> {
        furhat.say(" This defense is diversification, which means the patient changes topics in order to avoid " +
                " answering the question. You can usually tell it's diversification if the patient suddenly introduces " +
                " something unrelated into the conversation")
        goto(Wait3)
    }

    onResponse<Hint> {
        furhat.say(" The patient is reaching quite far to avoid mentioning a specific instance. What are they talking" +
                " about instead? Is it related at all to the topic at hand?")
        goto(Diversification3Goto)
    }


}


val Avoidance3Goto : State = state {
    onEntry {
        goto(Avoidance3)
    }
}


val Avoidance3 : State = state {

    val rand = Random()
    val num = rand.nextInt(5)

    onEntry {
        when (num) {
            0 -> furhat.say(" I really don't want to think about that, it's very painful")
            1 -> furhat.say(" I haven't thought of any incidents for a long time, and don't intend to think about " +
                    " it now or again.")
            2 -> furhat.say("You are asking me to dive into my feelings? That's not what I want, I thought therapy " +
                    "was supposed to make me happy and not go into negative emotions.")
            3 -> furhat.say(" I'd really prefer not to think about it, it causes so many negative feelings that " +
                    " I try to avoid.")
            4 -> furhat.say(" I have been doing a good job of avoiding these thoughts and feelings, and don't see " +
                    " a reason to go into them again.")
        }
    }

    onResponse<AvoidanceBlock3> {

        it.intent.avoid
        it.intent.feel

        when (num) {
            0 -> furhat.say(" That's right. The patient admits they prefer not to think about their painful feelings  " +
                    " which is a case of avoidance. However, the way to progress through therapy is to explore theses " +
                    " feelings even if they are temporarily painful")
            1 -> furhat.say(" Correct. When patients openly express that they are unwilling to explore their feelings, " +
                    " it is a good sign they are in a state of avoidance.")
            2 -> furhat.say(" Great job. When blocking avoidance, it is good to explain to patients that " +
                    " it is necessary to overcome their fear of exploring painful feelings in order to get better. ")
            3 -> furhat.say(" Exactly right. The patient even uses the word 'avoid' in this case. In general with" +
                    " avoidance, it is helpful to provide a safe environment in which they feel brave enough to explore" +
                    " feelings they usually avoid")
            4 -> furhat.say(" Perfect. In fact, all defenses are a type of avoidance. What signifies this defense is that " +
                    " patients openly admit to, and are consciously aware of their avoiding behavior. ")
        }

        goto(Counter3)
    }

    onResponse<GeneralizationBlock3> {
        furhat.say("Not quite. When patients generalize, they still talk about their issue but do so in an indirect " +
                " and general sense. In this example the patient completely evades the topic, try again in the next defense" +
                " in the same category.")
        goto(Avoidance3Goto)
    }

    onResponse<NoMemoryBlock3> {
        furhat.say(" Good guess. The patient is indeed failing to provide a concrete instance of their issue, but " +
                " in this case they are not blaming their lack of memory. See if you can spot the reason they don't " +
                " declare a specific example in the next defense of the same kind")
        goto(Avoidance3Goto)
    }

    onResponse<DiversificationBlock3> {
        furhat.say(" Not bad, but not quite right. In diversification the patient completely changes the topic " +
                " in order to confront their emotions. In this case the patient does evade the topic, but they are being " +
                " more direct about how they do it. Try again in this next defense")
        goto(Avoidance3Goto)
    }

    onResponse<TryAgain> {
        furhat.say(" Ok, let's try another defense")
        goto(Wait3)
    }

    onResponse<GiveAnswer> {
        furhat.say(" This defense is avoidance. Though all defenses are ultimately a form of avoidance, in these cases" +
                " the patients are consciously aware that they are avoiding their problem. It is helpful to remind them" +
                " how this strategy has not served them in the past, and that they need to confront their emotions in " +
                " order to improve. ")
        goto(DeclareSpecific)
    }

    onResponse<Hint> {
        furhat.say( " Notice that the patient seems rather aware of how they are evading their feelings and do it" +
                " quite consciously. This is distinctly different from other defense mechanisms, in which patients are " +
                " often unaware of their pattern. ")
        goto(Avoidance3Goto)
    }
}

val Intellectualization3Goto : State = state {
    onEntry {
        goto(Intellectualization3)
    }
}

val Intellectualization3 : State = state {

    val rand = Random()
    val num = rand.nextInt(5)

    onEntry {
        when (num) {
            0 -> furhat.say(" Last time it happened, I remember thinking how we really need to find a solution" +
                    " to this problem")
            1 -> furhat.say(" Sometimes when it happens, I try to use techniques from books about how to calm down, " +
                    " but it does not really work in the moment, you know. ")
            2 -> furhat.say(" Maybe if I had more emotional intelligence I would be able to handle those types" +
                    " of situations better, but I suppose I have to deal with what I have.")
            3 -> furhat.say( " It happened one time last weekend, and I remember being really on edge that day. And" +
                    " when I'm on edge I am less able to deal with confrontation. So there is that.")
            4 -> furhat.say(" A specific situation you say? How is that supposed to help? I mean, I have read " +
                    " that I need to deal with my childhood issues in order to solve problems like these.")
        }

    }

    onResponse<IntellecutalizationBlock3> {
        when (num) {
            0 -> furhat.say(" Great job. In intellectualization, the patient offers thoughts instead of feelings. " +
                    " Instead of describing the situation or their emotions, they share their thoughts on the matter.")
            1 -> furhat.say(" Perfect. In this case the patient starts talking about their thoughts and strategies" +
                    " of dealing with the situation, instead of simply describing the situation and their feelings about it.")
            2 -> furhat.say(" That's correct. The word 'if' is generally a good sign the patient is intellectualizing, " +
                    " since it is commonly followed by some sort of hypothetical thought pattern. ")
            3 -> furhat.say (" That's right. When blocking intellectualization, don't go along with the thoughts the " +
                    " patients offer. Gently make them aware of their defense and redirect their attention to what the " +
                    " theraputic process demands")
            4 -> furhat.say(" Indeed it is intellectualization. In this case, the patient is thinking about the " +
                    " grander scheme of therapy instead of the specific problem. However, they are still offering thoughts" +
                    " instead of feelings, which is the hallmark of intellectualization.")
        }
        goto(Counter3)
    }

    onResponse<RationalizationBlock1> {
        furhat.say(" Great guess. Rationalization and intellectualization are sometimes used interchangeably and " +
                " are very easy to confuse. Technically, in rationalization, patients attempt to justify their actions " +
                " rather than talk about how they felt. In intellectualization, patients focus on the rational side of " +
                " the problem instead of the emotion, offering thoughts instead of emotions. This subtle difference will" +
                " be explored in more detail in future modules")
        goto(Intellectualization3Goto)
    }

    onResponse<GeneralizationBlock3> {
        furhat.say(" Good guess. The patient is in a way generalizing by abstracting away from describing a specific" +
                " instance of their issue, but the key part is noticing what the patient talks about instead of offering " +
                " a specific scenario. Try catching it in the next defense of the same kind")
        goto(Intellectualization3Goto)
    }

    onResponse<NoMemoryBlock3> {
        furhat.say(" Not quite. In no memory blocks, the patient rather explicitly makes it clear that they cannot " +
                " remember about what you are asking. This is not quite the case here. Try again, and pay attention to " +
                " what the patient mentions instead of offering their emotions")
        goto(Intellectualization3Goto)
    }

    onResponse<DiversificationBlock3> {
        furhat.say(" Not bad. In a way the patient is changing the topic, but notice in what way they are avoiding " +
                " talking about a specific instance. Diversification suggests a rather abrupt change in topic, to what" +
                " is the patient switching attention to in this case?")
        goto(Intellectualization3Goto)
    }

    onResponse<AvoidanceBlock3> {
        furhat.say( " Nice try. The patient is indeed avoiding your inquiry, but the avoidance defense implies that " +
                " the patient is aware of avoiding the issue and makes it rather explicit. In this case, the patient " +
                " avoids describing a specific scenario in a more subtle way, try again in this next defense.")
        goto(Intellectualization3Goto)
    }

    onResponse<TryAgain> {
        furhat.say(" Ok, let's try another defense")
        goto(Wait3)
    }

    onResponse<GiveAnswer> {
        furhat.say(" This defense is intellectualization. In intellecualization the patient offers their thoughts" +
                " instead of their emotions. Notice how when asked about a specific scenario made them feel, they instead " +
                " mention their ideas and thoughts about it.Whenever patients mention ideas over feelings it is a good " +
                " sign they may be intellecutalizing.")
        goto(DeclareSpecific)
    }

    onResponse<Hint> {
        furhat.say(" Consider what kind of information the patient is giving you instead of describing a specific" +
                " instance of the problem, and how it made them feel. Are they centered in emotions or are they providing " +
                " you with another kind of information?")
        goto(Intellectualization3Goto)
    }
}






val Resolution3 : State = state {

    onEntry {
        furhat.say("Great! You helped patient to declare a specific instance in which their problem manifested. " +
                " Turns out they got angry with their father in relation to talking about their mother's illness. This" +
                " is exactly the type of valuable information that describing a particular instance may yield, and helps" +
                " progress the therapeutic process. How exactly to do this will be the topic of future modules")
    }
}



package com.joy.yariklab

data class User(
    val name: String,
    val age: Int,
) {

    override fun hashCode(): Int {
        return 1
    }
}

fun main() {
    val map = hashMapOf(
         User(
            name = "Yarik",
            age = 28,
        ) to 1,
         User(
            name = "Vasiya",
            age = 45,
        ) to 2,
        User(
            name = "Rzora",
            age = 24,
        ) to 3,
    )
    map.put(User(
        name = "Yarik",
        age = 28,
    ), 34)
    map.hashCode()
    //println(map.get(1))
    // TestExpl.yarikMain()
    /*val cache = InMemoryCache<Int, String>()

    val scope = CoroutineScope(Job())

    val job1 = scope.launch {
        cache.subscribeOn(1)
            .collect {
                println(it)
            }
    }

    val job2 = scope.launch {
        cache.subscribeOn(2)
            .collect {
                // println(it)
            }
    }

    val job = scope.launch {
        coroutineScope {
            val deferred1 = async {
                withContext(Dispatchers.IO) {
                    cache.apply {
                        addName(1, "Yarik")
                        addName(1, "Vasya")
                        delay(100)
                        addName(1, "Dima")
                        addName(1, "Oleg")

                        delay(100)

                        removeName(2, "Vova")
                        removeName(2, "Zuzya")
                        remove(2)
                    }
                }
            }

            val deferred2 = async {
                withContext(Dispatchers.IO) {
                    cache.apply {
                        addName(2, "Rzora")
                        addName(2, "Vova")

                        delay(50)

                        addName(2, "Zuzya")
                        removeName(1, "Yarik")
                    }
                }
            }

            deferred1.await()
            deferred2.await()
        }
    }

    runBlocking {
        job1.join()
        job2.join()

        job.join()
    }*/
}
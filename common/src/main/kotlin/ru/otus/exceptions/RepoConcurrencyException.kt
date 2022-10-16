package ru.otus.exceptions

import ru.otus.model.MkplLock

class RepoConcurrencyException(expectedLock: MkplLock, actualLock: MkplLock?) : RuntimeException(
	"Expected lock is $expectedLock while actual lock in db is $actualLock"
)

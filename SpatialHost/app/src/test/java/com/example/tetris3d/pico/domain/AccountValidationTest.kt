package com.example.tetris3d.pico.domain
import com.example.tetris3d.pico.domain.usecase.AccountValidation
import org.junit.Assert.*
import org.junit.Test
class AccountValidationTest {
 @Test fun acceptsEmailAndRejectsWhitespace() {assertTrue(AccountValidation.email("player@example.com"));assertFalse(AccountValidation.email("a b@example.com"));assertFalse(AccountValidation.email("abc"))}
 @Test fun registrationNeedsSixDigitCode() {assertNull(AccountValidation.error("a@b.com","12345678","123456",true));assertNotNull(AccountValidation.error("a@b.com","12345678","12",true))}
}

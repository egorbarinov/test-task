package ru.egorbarinov.testtask;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnluckyVassalTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    UnluckyVassal unluckyVassal;

    private List<String> pollResults;
    private List<String> pollResultsForGeneratingException;

    private String report = "король\r\n" +
            "    аристократ Клаус\r\n" +
            "    дворянин Кузькин\r\n" +
            "        жена Кузькина\r\n" +
            "        управляющий Семен Семеныч\r\n" +
            "            доярка Нюра\r\n" +
            "            крестьянин Федя\r\n" +
            "        экономка Лидия Федоровна\r\n" +
            "            дворник Гена\r\n" +
            "                посыльный Тошка\r\n" +
            "                    кот Василий\r\n" +
            "                        человеческая особь Катя\r\n" +
            "            служанка Аня\r\n" +
            "    киллер Гена\r\n" +
            "    просветленный Антон\r\n" +
            "    секретарь короля\r\n" +
            "        зажиточный холоп\r\n" +
            "            крестьянка Таня\r\n" +
            "        шпион Т\r\n" +
            "            кучер Д";

    @BeforeEach
    public void setUp() {
        unluckyVassal = new UnluckyVassal();
        pollResults = Arrays.asList("служанка Аня", "управляющий Семен Семеныч: крестьянин Федя, доярка Нюра",
                "дворянин Кузькин: управляющий Семен Семеныч, жена Кузькина, экономка Лидия Федоровна",
                "экономка Лидия Федоровна: дворник Гена, служанка Аня",
                "доярка Нюра",
                "кот Василий: человеческая особь Катя",
                "дворник Гена: посыльный Тошка",
                "киллер Гена",
                "зажиточный холоп: крестьянка Таня",
                "секретарь короля: зажиточный холоп, шпион Т",
                "шпион Т: кучер Д",
                "посыльный Тошка: кот Василий",
                "аристократ Клаус",
                "просветленный Антон"
        );

        pollResultsForGeneratingException = Arrays.asList(
                "служанка Аня",
                "управляющий Семен Семеныч: крестьянин Федя, доярка Нюра",
                "дворянин Кузькин: управляющий Семен Семеныч, жена Кузькина, экономка Лидия Федоровна",
                "экономка Лидия Федоровна: дворник Гена, служанка Аня",
                "доярка Нюра: управляющий Семен Семеныч",
                "кот Василий: человеческая особь Катя",
                "дворник Гена: посыльный Тошка",
                "киллер Гена",
                "зажиточный холоп: крестьянка Таня",
                "секретарь короля: зажиточный холоп, шпион Т",
                "шпион Т: кучер Д",
                "посыльный Тошка: кот Василий",
                "аристократ Клаус",
                "просветленный Антон"
        );
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void givenPrintReportForKing_whenInvokePrintln_thenOutputCaptorSuccess() throws CyclicSubmissionException {
        unluckyVassal.printReportForKing(pollResults);
        assertEquals(report, outputStreamCaptor.toString().trim());
    }

    @Test
    void givenPrintReportForKing_whenDerivedExceptionThrown_thenAssertionSuccess() {
        Exception exception = assertThrows(CyclicSubmissionException.class, () -> {
            unluckyVassal.printReportForKing(pollResultsForGeneratingException);
        });
        String expectedMessage = "critical error: cyclic submission";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}

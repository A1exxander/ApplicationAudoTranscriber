package AudioTranscriber;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.vosk.Recognizer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VoskAudioTranscriberTest {

    private AudioFormat audioFormat;
    private Recognizer recognizerMock;
    private VoskAudioTranscriber voskAudioTranscriber;

    @BeforeEach
    void setUp() {
        audioFormat = new AudioFormat(16000, 16, 1, true, false);
        recognizerMock = Mockito.mock(Recognizer.class);
        voskAudioTranscriber = new VoskAudioTranscriber(audioFormat, recognizerMock);
    }

    @AfterEach
    void tearDown() {
        voskAudioTranscriber = null;
    }

    @Nested
    class TranscribeTests {

        @ParameterizedTest
        @NullAndEmptySource
        void shouldThrowWhenAudioBytesAreInvalid(byte[] audioBytes) {
            assertThrows(IllegalArgumentException.class, ()-> voskAudioTranscriber.transcribe(audioBytes));
        }

        @Test
        void shouldTranscribeSuccessfully() throws IOException {
            // Arrange
            byte[] audioBytes = new byte[8192];
            ByteArrayInputStream audioByteInputStream = new ByteArrayInputStream(audioBytes);

            when(recognizerMock.acceptWaveForm(any(byte[].class), anyInt()))
                    .thenReturn(false)
                    .thenReturn(true)  // Second chunk produces a result
                    .thenReturn(false); // Third chunk doesn't produce a result

            // Simulate the recognizer returning an intermediate and final result
            when(recognizerMock.getResult())
                    .thenReturn("transcribed text"); // Result after second chunk
            when(recognizerMock.getFinalResult())
                    .thenReturn(" final text"); // Final result after all chunks

            // Act
            String result = voskAudioTranscriber.transcribe(audioBytes);

            // Assert
            assertEquals("transcribed text final text", result);

            // Verify the interactions
            verify(recognizerMock, times(3)).acceptWaveForm(any(byte[].class), anyInt());
            verify(recognizerMock, times(1)).getResult(); // Called only once when acceptWaveForm returns true
            verify(recognizerMock, times(1)).getFinalResult(); // Called at the end

        }
    }
}
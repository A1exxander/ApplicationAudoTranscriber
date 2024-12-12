package AudioRecorder;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import javax.sound.sampled.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.junit.jupiter.api.Assertions.*;

class AudioRecorderTest {

    private AudioFormat audioFormat;
    private TargetDataLine audioInputLine;
    private ExecutorService recordAudioExecutor;
    private AudioRecorder audioRecorder;

    @BeforeEach
    void setUp() throws LineUnavailableException {
        audioFormat = new AudioFormat(16000, 16, 1,true,  false);
        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
        audioInputLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
        recordAudioExecutor = Executors.newSingleThreadExecutor(); // We can't mock this w/o blowing some shit up
        audioRecorder = new AudioRecorder(audioFormat, audioInputLine, recordAudioExecutor);
    }

    @AfterEach
    void tearDown() {
        if(audioRecorder.isRecording()){
            audioRecorder.stopRecording();
        }
        audioRecorder = null;
    }

    @Nested
    class RecordTests {

        @Test
        void shouldStartRecordingSuccessfully() throws LineUnavailableException {
            audioRecorder.record();
            assertAll(()-> assertTrue(audioInputLine.isOpen()),
                      ()-> audioInputLine.isActive(),
                      ()-> audioRecorder.isRecording()
            );
        }

        @Test
        void shouldThrowWhenTryingToRecordWhenAlreadyRecording() throws LineUnavailableException {
            audioRecorder.record();
            assertThrows(IllegalStateException.class, ()-> audioRecorder.record());
        }

    }

    @Nested
    class StopRecordingTests {

        @Test
        void shouldStopRecordingWhenRecording() throws LineUnavailableException {
            audioRecorder.record();
            byte[] recordedAudio = audioRecorder.stopRecording();
            assertAll(()-> assertNotNull(recordedAudio),
                      ()-> assertFalse(audioInputLine.isOpen()),
                      ()-> assertFalse(audioInputLine.isActive()));
        }

        @Test
        void shouldThrowWhenNotRecording() {
            assertThrows(IllegalStateException.class, ()-> audioRecorder.stopRecording());
        }

    }

    @Nested
    class SetAudioInputLineTests {

        @Test
        void shouldThrowWhenRecording() throws LineUnavailableException {
            audioRecorder.record();
            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
            TargetDataLine newAudioLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            assertThrows(IllegalStateException.class, ()-> audioRecorder.setAudioInputLine(newAudioLine));
        }

        @Test
        void shouldThrowWhenSettingToNull() throws LineUnavailableException {
            assertThrows(NullPointerException.class, ()-> audioRecorder.setAudioInputLine(null));
        }

    }

}
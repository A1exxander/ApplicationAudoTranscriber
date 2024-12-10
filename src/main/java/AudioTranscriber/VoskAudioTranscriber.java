package AudioTranscriber;

import org.vosk.Model;
import org.vosk.Recognizer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class VoskAudioTranscriber implements iAudioTranscriber {

    private final Model audioTranscriptionModel;
    private final Recognizer recognizer;

    public VoskAudioTranscriber(Model audioTranscriptionModel) throws IOException {
        this.audioTranscriptionModel = audioTranscriptionModel;
        this.recognizer = new Recognizer(audioTranscriptionModel, 16000);
    }

    @Override
    public String transcribe(byte[] audioBytes) throws IOException {

        ByteArrayInputStream audioByteInputStream = new ByteArrayInputStream(audioBytes);
        AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
        AudioInputStream audioInputStream = new AudioInputStream(audioByteInputStream, format, audioBytes.length / format.getFrameSize());

        byte[] buffer = new byte[4096];
        StringBuilder transcribedText = new StringBuilder();

        int bytesRead;
        while ((bytesRead = audioInputStream.read(buffer)) != -1) {
            if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                transcribedText.append(recognizer.getResult());
            }
        }

        transcribedText.append(recognizer.getFinalResult());
        return transcribedText.toString();

    }

}

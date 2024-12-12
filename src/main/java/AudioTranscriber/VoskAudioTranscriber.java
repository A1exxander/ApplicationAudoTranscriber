package AudioTranscriber;

import org.vosk.Model;
import org.vosk.Recognizer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class VoskAudioTranscriber implements iAudioTranscriber {

    private final AudioFormat audioFormat;
    private final Model model;
    private final Recognizer recognizer;

    public VoskAudioTranscriber(Model model, AudioFormat audioFormat) throws IOException {
        this.audioFormat = audioFormat;
        this.model = model;
        this.recognizer = new Recognizer(model, audioFormat.getSampleRate());
    }

    @Override
    public String transcribe(byte[] audioBytes) throws IOException {

        if (audioBytes == null || audioBytes.length == 0){
            throw new IllegalArgumentException("Invalid audio bytes array.");
        }

        ByteArrayInputStream audioByteInputStream = new ByteArrayInputStream(audioBytes);
        AudioInputStream audioInputStream = new AudioInputStream(audioByteInputStream, audioFormat, audioBytes.length / audioFormat.getFrameSize());

        byte[] buffer = new byte[4096];
        StringBuilder transcribedText = new StringBuilder();

        int bytesRead = audioInputStream.read(buffer);
        while (bytesRead != -1) {
            if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                transcribedText.append(recognizer.getResult());
            }
            bytesRead = audioInputStream.read(buffer);
        }

        transcribedText.append(recognizer.getFinalResult());
        return transcribedText.toString();

    }

}

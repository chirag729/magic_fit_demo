package fit.magic.android;

import com.google.mediapipe.formats.proto.LandmarkProto;

import java.util.List;

public interface LandmarksListListener {
    void listUpdated(List<LandmarkProto.NormalizedLandmark> landmarks);
}

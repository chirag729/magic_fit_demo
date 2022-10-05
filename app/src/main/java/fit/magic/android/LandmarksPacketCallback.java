package fit.magic.android;

import com.google.mediapipe.formats.proto.LandmarkProto;
import com.google.mediapipe.framework.Packet;
import com.google.mediapipe.framework.PacketCallback;
import com.google.mediapipe.framework.PacketGetter;
import com.google.protobuf.InvalidProtocolBufferException;

public class LandmarksPacketCallback implements PacketCallback  {

    private LandmarksListListener listener = null;

    public void setListener(LandmarksListListener listener) {
        this.listener = listener;
    }

    @Override
    public void process(Packet packet) {

        try {
            LandmarkProto.NormalizedLandmarkList list = LandmarkProto.NormalizedLandmarkList.parseFrom(PacketGetter.getProtoBytes(packet));

            if(listener != null) {
                listener.listUpdated(list.getLandmarkList());
            }

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}

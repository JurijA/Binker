package be.kuleuven.objects;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

public class Group {

    String groupName;
    Integer groupId;
    Integer lastActive;
    List <User> groupMembers;
    String groupPhoto;

    public Group(String groupName, Integer groupId, List<User> groupMembers, String groupPhoto) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.groupMembers = groupMembers;
        this.groupPhoto = groupPhoto;
    }

    @NonNull
    @Override
    public String toString() {
        return "Group{" +
                "groupName='" + groupName + '\'' +
                ", groupId=" + groupId +
                ", lastActive=" + lastActive +
                ", groupMembers=" + groupMembers +
                ", groupPhoto='" + groupPhoto + '\'' +
                '}';
    }

    public String getGroupName() {
        return groupName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public Integer getLastActive() {
        return lastActive;
    }

    public List<User> getGroupMembers() {
        return groupMembers;
    }

    public String getGroupPhoto() {
        return groupPhoto;
    }
}

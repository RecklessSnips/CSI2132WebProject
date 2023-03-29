package Entities;

// DO NOT REMOVE ANY TAGS, ONLY ADD
public enum RoomTags {
    TV,
    AirConditioning,
    Microwave,
    Fridge,
    SmokingAllowed,
    PetsFriendly,
    SeaView,
    MountainView;

    public int flag(){
        return 1 << this.ordinal();
    }

    public boolean checkIfTagIsPresent(int tags) {
        return ((1 << this.ordinal()) | tags) > 0;
    }
}

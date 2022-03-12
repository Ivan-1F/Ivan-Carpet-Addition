package me.ivan.ivancarpetaddition.commands.xpcounter.interfaces;

import me.ivan.ivancarpetaddition.commands.xpcounter.SpawnReason;

public interface IExperienceOrbEntity {
    void setSpawnReason(SpawnReason reason);
    SpawnReason getSpawnReason();
}

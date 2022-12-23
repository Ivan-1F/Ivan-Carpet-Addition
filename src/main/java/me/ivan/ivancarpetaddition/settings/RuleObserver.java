package me.ivan.ivancarpetaddition.settings;


import carpet.api.settings.CarpetRule;
import carpet.api.settings.Validator;
import net.minecraft.server.command.ServerCommandSource;

public abstract class RuleObserver<T> extends Validator<T> {
    @Override
    public T validate(ServerCommandSource source, CarpetRule<T> currentRule, T newValue, String string) {
        if (currentRule.value() != newValue) {
            this.onValueChanged(currentRule.value(), newValue);
        }
        return newValue;
    }

    abstract public void onValueChanged(T oldValue, T newValue);
}

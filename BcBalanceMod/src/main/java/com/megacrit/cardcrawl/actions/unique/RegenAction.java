package com.megacrit.cardcrawl.actions.unique;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class RegenAction extends AbstractGameAction
{
    public String regenId = "Regeneration";
    
    public RegenAction(AbstractCreature target, int amount)
    {
        this.target = target;
        this.amount = amount;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    public void update()
    {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT)
        {
            this.isDone = true;
        }
        else
        {
            if (this.duration == Settings.ACTION_DUR_FAST)
            {
                if (this.target.currentHealth > 0)
                {
                    this.target.tint.color = Color.CHARTREUSE.cpy();
                    this.target.tint.changeColor(Color.WHITE.cpy());
                    this.target.heal(this.amount, true);
                }
                
                if (this.target.isPlayer)
                {
                    AbstractPower power = this.target.getPower(regenId);
                    if (power != null)
                    {
                        --power.amount;
                        if (power.amount == 0)
                        {
                            this.target.powers.remove(power);
                        }
                        else
                        {
                            power.updateDescription();
                        }
                    }
                }
            }
            
            this.tickDuration();
        }
    }
}

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.*;

import java.util.Iterator;

public class ChangeStanceAction extends AbstractGameAction
{
    private String id;
    private AbstractStance newStance;
    
    public ChangeStanceAction(String stanceId)
    {
        id = stanceId;
        newStance = AbstractStance.getStanceFromName(id);
        duration = Settings.ACTION_DUR_FAST;
    }
    
    public ChangeStanceAction(AbstractStance newStance)
    {
        id = newStance.ID;
        this.newStance = newStance;
        duration = Settings.ACTION_DUR_FAST;
    }
    
    public void update()
    {
        if (duration == Settings.ACTION_DUR_FAST)
        {
            if (AbstractDungeon.player.hasPower("CannotChangeStancePower"))
            {
                isDone = true;
                return;
            }
            
            AbstractStance oldStance = AbstractDungeon.player.stance;
            if (!oldStance.ID.equals(id))
            {
                if (newStance == null)
                {
                    newStance = AbstractStance.getStanceFromName(id);
                }
                
                Iterator var2 = AbstractDungeon.player.powers.iterator();
                
                while (var2.hasNext())
                {
                    AbstractPower p = (AbstractPower) var2.next();
                    p.onChangeStance(oldStance, newStance);
                }
                
                var2 = AbstractDungeon.player.relics.iterator();
                
                while (var2.hasNext())
                {
                    AbstractRelic r = (AbstractRelic) var2.next();
                    r.onChangeStance(oldStance, newStance);
                }
                
                oldStance.onExitStance();
                AbstractDungeon.player.stance = newStance;
                newStance.onEnterStance();
                if (AbstractDungeon.actionManager.uniqueStancesThisCombat.containsKey(newStance.ID))
                {
                    int currentCount = (Integer) AbstractDungeon.actionManager.uniqueStancesThisCombat.get(newStance.ID);
                    AbstractDungeon.actionManager.uniqueStancesThisCombat.put(newStance.ID, currentCount + 1);
                }
                else
                {
                    AbstractDungeon.actionManager.uniqueStancesThisCombat.put(newStance.ID, 1);
                }
                
                AbstractDungeon.player.switchedStance();
                
                for (AbstractCard card : AbstractDungeon.player.hand.group)
                {
                    card.onStanceChange(newStance);
                }
                
                for (AbstractCard card : AbstractDungeon.player.discardPile.group)
                {
                    card.onStanceChange(newStance);
                }
                
                for (AbstractCard card : AbstractDungeon.player.drawPile.group)
                {
                    card.onStanceChange(newStance);
                }
                
                AbstractDungeon.player.onStanceChange(id);
            }
            else if (newStance.ID.equals(DivinityStance.STANCE_ID))
            {
                //special case to allow divinity to generate energy even if you're already in divinity. doesn't trigger other stance change effects.
                ((DivinityStance) newStance).onRenteringStance();
            }
            
            AbstractDungeon.onModifyPower();
            if (Settings.FAST_MODE)
            {
                isDone = true;
                return;
            }
        }
        
        tickDuration();
    }
}

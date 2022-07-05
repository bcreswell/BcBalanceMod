package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.*;

public class GhostifyBlockAction extends AbstractGameAction
{
    AbstractCreature owner;
    
    public GhostifyBlockAction(AbstractCreature owner)
    {
        this.owner = owner;
    }
    
    public void update()
    {
        //if no longer intangible, return block to normal
        if (owner.getPower(IntangiblePlayerPower.POWER_ID) == null)
        {
            AbstractPower ghostlyBlockPower = owner.getPower(GhostlyBlockPower.POWER_ID);
            if ((ghostlyBlockPower != null) && (ghostlyBlockPower.amount > 0))
            {
                AbstractPower juggernautPower = owner.getPower(JuggernautPower.POWER_ID);
                if (juggernautPower != null)
                {
                    //the block gain from turning ghostly block back into normal block shouldn't trigger juggernaut to deal damage.
                    ((JuggernautPower)juggernautPower).SkipNextBlockGain = true;
                }
                addToBot(new GainBlockAction(owner, ghostlyBlockPower.amount));
                addToBot(new RemoveSpecificPowerAction(owner, owner, GhostlyBlockPower.POWER_ID));
            }
        }
        else if (owner.currentBlock > 0)
        {
            //otherwise, ghostify their current block
            addToBot(new BcApplyPowerAction(new GhostlyBlockPower(owner, owner.currentBlock)));
            owner.loseBlock(owner.currentBlock, true);
        }
        
        isDone = true;
    }
}

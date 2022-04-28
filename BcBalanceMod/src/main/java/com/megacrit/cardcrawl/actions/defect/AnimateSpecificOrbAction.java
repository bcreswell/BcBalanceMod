//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class AnimateSpecificOrbAction extends AbstractGameAction
{
    private AbstractOrb orb;
    
    public AnimateSpecificOrbAction(AbstractOrb orb)
    {
        this.orb = orb;
    }
    
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        for (int i = 0; i < player.orbs.size(); ++i)
        {
            if (player.orbs.get(i) == orb)
            {
                AbstractDungeon.player.triggerEvokeAnimation(i);
                break;
            }
        }
        
        this.isDone = true;
    }
}

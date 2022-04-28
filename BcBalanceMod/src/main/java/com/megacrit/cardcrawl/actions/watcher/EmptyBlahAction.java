//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.watcher;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.stances.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class EmptyBlahAction extends AbstractGameAction
{
    public static final String EmptyDescription;
    public static final int MantraPerExit = 3;
    
    static
    {
        EmptyDescription = "*Wrath: + [W] . NL *Calm: + [W] , Draw 1. NL *Divinity: +" + MantraPerExit + " *Mantra. NL Exit your Stance.";
    }
    
    public EmptyBlahAction()
    {
        actionType = ActionType.WAIT;
    }
    
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        boolean inAStance = false;
        if (BcUtility.isPlayerInStance(WrathStance.STANCE_ID))
        {
            addToBot(new GainEnergyAction(1));
            inAStance = true;
        }
        else if (BcUtility.isPlayerInStance(CalmStance.STANCE_ID))
        {
            addToBot(new GainEnergyAction(1));
            addToBot(new DrawCardAction(1));
            inAStance = true;
        }
        else if (BcUtility.isPlayerInStance(DivinityStance.STANCE_ID))
        {
            addToBot(new ApplyPowerAction(player, player, new MantraPower(player, MantraPerExit), MantraPerExit));
            inAStance = true;
        }
        
        AbstractRelic emptyVessel = BcUtility.getPlayerRelic(EmptyVessel.ID);
        if (inAStance && (emptyVessel != null))
        {
            emptyVessel.flash();
            addToBot(new GainEnergyAction(1));
        }
        
        
        addToBot(new NotStanceCheckAction(NeutralStance.STANCE_ID, new VFXAction(new EmptyStanceEffect(player.hb.cX, player.hb.cY), 0.1F)));
        addToBot(new ChangeStanceAction(NeutralStance.STANCE_ID));
        
        isDone = true;
    }
}

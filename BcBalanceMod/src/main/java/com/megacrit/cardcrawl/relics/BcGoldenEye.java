package com.megacrit.cardcrawl.relics;

import bcBalanceMod.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.watcher.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class BcGoldenEye extends AbstractRelic
{
    public static final String ID = BcBalanceMod.makeID("BcGoldenEye");
    public static final int ScryAmount = 3;
    
    public BcGoldenEye()
    {
        super(ID, "golden_eye.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.HEAVY);
    }
    
    public String getUpdatedDescription()
    {
        return "At the start of your turn, NL Scry #b" + ScryAmount + ".";
    }
    
    public void atTurnStart()
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        if (player != null)
        {
            this.flash();
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            
            int scryAmount = BcUtility.getActualScryAmount(ScryAmount);
    
            addToBot(new VFXAction(new ThirdEyeEffect(player.hb.cX, player.hb.cY)));
            addToBot(new ScryAction(scryAmount));
        }
    }
    
    public AbstractRelic makeCopy()
    {
        return new BcGoldenEye();
    }
}

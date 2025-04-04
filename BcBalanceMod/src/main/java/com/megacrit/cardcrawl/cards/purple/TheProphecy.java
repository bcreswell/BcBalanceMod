package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class TheProphecy extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("TheProphecy");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "The Prophecy";
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/theProphecy.png";
    }
    
    @Override
    public int getCost()
    {
        return !upgraded ? 2 : 1;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Choose 1 of your cards. NL The next time you draw it, enter Divinity. NL Shuffle ALL of your cards into the draw pile.";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(new ThirdEyeEffect(player.hb.cX, player.hb.cY)));
        addToBot(new TheProphecyAction());
        
        //this is how reboot shuffles all
        addToBot(new ShuffleAllAction());
        addToBot(new ShuffleAction(AbstractDungeon.player.drawPile, false));
    }
}

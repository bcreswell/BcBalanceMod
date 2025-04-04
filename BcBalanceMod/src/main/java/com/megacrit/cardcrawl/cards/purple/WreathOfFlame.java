package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class WreathOfFlame extends BcSkillCardBase
{
    public static final String ID = "WreathOfFlame";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/skill/wreathe_of_flame";
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 4 : 6;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }

    int getScryAmount()
    {
        return 3;
    }

    @Override
    public String getBaseDescription()
    {
        return "Scry "+BcUtility.getScryString(getScryAmount())+". NL Your next Attack played while in Wrath or Divinity will deal + !M! damage.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(new ThirdEyeEffect(player.hb.cX, player.hb.cY)));
        addToBot(new ScryAction(getScryAmount()));
        
        addToBot(new VFXAction(player, new FlameBarrierEffect(player.hb.cX, player.hb.cY), 0.5F));
        addToBot(new BcApplyPowerAction(new WreathOfFlamePower(player, magicNumber)));
    }
}

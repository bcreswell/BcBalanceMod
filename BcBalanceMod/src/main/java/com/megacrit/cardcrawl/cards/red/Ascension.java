//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.*;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;

public class Ascension extends BcPowerCardBase
{
    public static final String ID = BcBalanceMod.makeID("Ascension");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Ascension";
    }
    
    @Override
    public String getImagePath()
    {
        return "red/ascension.png";
    }
    
    @Override
    protected void onInitialized()
    {
        tags.add(AbstractCard.CardTags.HEALING);
    }
    
    @Override
    public int getCost()
    {
        return !upgraded ? 3 : 2;
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
    public boolean getInnate()
    {
        return true;
    }
    
    @Override
    public boolean getEthereal()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        //str per card
        return 1;
    }
    
    int getSacrificeHp()
    {
        return 3;
    }

    @Override
    public String getBaseDescription()
    {
        int sacrificeHp = getSacrificeHp();
        String desc = "When you lose HP from a card, gain !M! Strength. NL When you play an Attack, heal for !M!.";
        if (sacrificeHp > 0)
        {
            desc += " NL Sacrifice " + sacrificeHp + " HP.";
        }

        return desc;
    }
    //endregion

    public void use(AbstractPlayer player, AbstractMonster m)
    {
        addToBot(new BcApplyPowerAction(new AscensionPower(player, magicNumber)));
        
        int sacrificeHp = getSacrificeHp();
        if (sacrificeHp > 0)
        {
            addToBot(new VFXAction(new OfferingEffect(), 0.1F));
            addToBot(new LoseHPAction(player, player, sacrificeHp));
        }
    }
}

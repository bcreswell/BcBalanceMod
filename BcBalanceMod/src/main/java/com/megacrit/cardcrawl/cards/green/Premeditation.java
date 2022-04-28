//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Premeditation extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("Premeditation");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Premeditation";
    }
    
    @Override
    public void onInitialized()
    {
        this.cardsToPreview = new Shiv();
    }
    
    @Override
    public String getImagePath()
    {
        return "green/premeditation.png";
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
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 1 : 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (getMagicNumber() == 1)
        {
            return "Discard any number of cards from your hand. NL Create !M! *Hidden *Shiv for each discard.";
        }
        else
        {
            return "Discard any number of cards from your hand. NL Create !M! *Hidden *Shivs for each discard.";
        }
    }
    
    @Override
    public String getTemporaryExtraDescription()
    {
        int maxDiscardCount = AbstractDungeon.player.hand.size() - 1;
        return "(max discards: " + maxDiscardCount + ")";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        addToTop(new PremeditationAction(magicNumber));
    }
}

package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class Inspiration extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("Inspiration");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Inspiration";
    }
    
    @Override
    public void onInitialized()
    {
        cardsToPreview = new Insight();
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/inspiration.png";
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
    public int getMagicNumber()
    {
        return 5;
    }
    
    int getInsightCount()
    {
        return 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Scry !M!. NL Draw until your hand is full. NL Add " + getInsightCount() + " Insights to your draw pile.";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (player != null)
        {
            addToBot(new VFXAction(new ThirdEyeEffect(player.hb.cX, player.hb.cY)));
        }
    
        int scryAmount = BcUtility.getActualScryAmount(magicNumber);
        addToBot(new ScryAction(scryAmount));
        
        addToBot(new ExpertiseAction(player, 10));
        
        int insightCount = getInsightCount();
        addToBot(new MakeTempCardInDrawPileAction(cardsToPreview.makeStatEquivalentCopy(), insightCount, true, true));
    }
}

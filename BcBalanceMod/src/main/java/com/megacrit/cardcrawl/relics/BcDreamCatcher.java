//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.relics;

import basemod.abstracts.CustomRelic;
import bcBalanceMod.BcBalanceMod;
import bcBalanceMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;

import java.util.*;

import static bcBalanceMod.BcBalanceMod.makeRelicPath;

public class BcDreamCatcher extends CustomRelic
{
    public static final String ID = BcBalanceMod.makeID("BcDreamCatcher");
    public static final int ChoiceCount = 10;
    boolean waitingOnSelection;
    
    public BcDreamCatcher()
    {
        super(ID, "dreamCatcher.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.MAGICAL);
    }
    
    public String getUpdatedDescription()
    {
        return "Whenever you #yRest, you may add 1 of " + ChoiceCount + " non-Rare cards to your deck.";
    }
    
    @Override
    public void onEnterRoom(AbstractRoom room)
    {
        waitingOnSelection = false;
    }
    
    public void onRest()
    {
        flash();
        
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        
        while (group.size() < ChoiceCount)
        {
            AbstractCard card = AbstractDungeon.getCard(AbstractDungeon.rollRarity()).makeCopy();
            
            for (AbstractCard existingCard : group.group)
            {
                if (existingCard.cardID.equals(card.cardID))
                {
                    card = null;
                    break;
                }
            }
            
            if ((card != null) && (card.rarity == AbstractCard.CardRarity.RARE))
            {
                //too powerful if you tend to get a rare every time you rest
                card = null;
            }
            
            if (card != null)
            {
                UnlockTracker.markCardAsSeen(card.cardID);
                group.addToBottom(card);
            }
        }
        
        waitingOnSelection = true;
        AbstractDungeon.gridSelectScreen.open(
                group,
                1,
                "Choose one to add to your deck.",
                false,
                false,
                true,
                false);
    }
    
    public void update()
    {
        if (waitingOnSelection &&
                    (AbstractDungeon.gridSelectScreen.selectedCards.size() > 0))
        {
            waitingOnSelection = false;
            
            for(AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards)
            {
                AbstractDungeon.topLevelEffects.add(
                        new ShowCardAndObtainEffect(
                                card,
                                (float) Settings.WIDTH / 2.0F,
                                (float) Settings.HEIGHT / 2.0F));
            }
            
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        
        super.update();
    }
    
    public boolean canSpawn()
    {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }
}

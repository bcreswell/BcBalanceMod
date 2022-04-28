//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.relics;

import basemod.abstracts.CustomRelic;
import bcBalanceMod.*;
import bcBalanceMod.util.TextureLoader;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.ui.campfire.*;

import java.util.*;

import static bcBalanceMod.BcBalanceMod.makeRelicPath;

public class BcBalancingScales extends CustomRelic
{
    public static final String ID = BcBalanceMod.makeID("BcBalancingScales");
    
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("bcBalancingScales.png"));
    // static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("bcBalancingScales.png"));
    public static final int InitialRemovalCost = 50;
    public static final int InitialRemovalCostA16 = 75;
    public static final int RemovalCostIncrement = 25;
    public static final int RemovalCostIncrementA16 = 25;
    public static final int RitualCurseRemovalHpCost = 5;
    
    int removalCountForThisShop;
    boolean isPlayerInAShop;
    int previousDeckSize = -1;
    public static final Color corruptedGlow = new Color(1f, 0, 1f, 1);
    boolean alreadyDizzyThisCard = false;
    
    public BcBalancingScales()
    {
        super(ID, IMG, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }
    
    @Override
    public String getUpdatedDescription()
    {
        return "Adds #b1 extra choice to card rewards. NL NL The Merchant's #bCard #bRemoval #bService starts at #y" + getInitialRemovalCost() + "g, increments by #y" + getRemovalCostIncrement() + "g and resets for each new shop.";
    }
    
    @Override
    public void onRefreshHand()
    {
        for (AbstractCard card : AbstractDungeon.player.hand.group)
        {
            if (card.isEthereal)
            {
                if (!card.glowColor.equals(InfernalBlade.infernalColor))
                {
                    BcUtility.setGlowColor(card, corruptedGlow);
                }
            }
        }
    }
    
    @Override
    public void onEnterRoom(AbstractRoom room)
    {
        removalCountForThisShop = 0;
        previousDeckSize = AbstractDungeon.player.masterDeck.size();
    }
    
    @Override
    public void onMasterDeckChange()
    {
        if (isPlayerInAShop)
        {
            int deckSize = AbstractDungeon.player.masterDeck.size();
            if (previousDeckSize > deckSize)
            {
                removalCountForThisShop++;
            }
            
            previousDeckSize = deckSize;
        }
    }
    
    @Override
    public void update()
    {
        super.update();
        if ((AbstractDungeon.player != null) &&
                    (AbstractDungeon.player.masterDeck != null))
        {
            if (previousDeckSize == -1)
            {
                previousDeckSize = AbstractDungeon.player.masterDeck.size();
            }
            isPlayerInAShop = AbstractDungeon.getCurrRoom() instanceof ShopRoom;
            AbstractDungeon.shopScreen.purgeAvailable = true;
            ShopScreen.purgeCost = getCurrentRemovalCost();
            ShopScreen.actualPurgeCost = getCurrentRemovalCost();
        }
    }
    
    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster monster)
    {
        alreadyDizzyThisCard = false;
    }
    
    @Override
    public void onCardDraw(AbstractCard drawnCard)
    {
        if (alreadyDizzyThisCard)
        {
            return;
        }
        
        AbstractPlayer player = AbstractDungeon.player;
        
        // using "when you empty your draw pile" instead of "on shuffle" because shuffling can stack up in weird ways
        // this is to prevent it from stacking up in unexpected ways
        if (BcUtility.isPlayerInCombat() && (AbstractDungeon.ascensionLevel >= 13))
        {
            //we just drew the last card
            if (player.drawPile.size() == 0)
            {
                alreadyDizzyThisCard = true;
                int dizzyAmount = BcUtility.getPowerAmount(DizzyPower.POWER_ID);
                if (dizzyAmount + DizzyPower.DizzyPerEmptyDrawPile > DizzyPower.NauseousTheshold)
                {
                    addToBot(new MakeTempCardInDiscardAction(new Nausea(), 1));
                }
                else
                {
                    BcApplyPowerAction applyDizzyAction = new BcApplyPowerAction(new DizzyPower(player, DizzyPower.DizzyPerEmptyDrawPile));
                    applyDizzyAction.makeQuiet();
                    
                    //this adds to top so that the dizzy is in place before
                    // the next card is drawn, so that it can be properly reduced by it
                    addToTop(applyDizzyAction);
                }
            }
        }
    }
    
    @Override
    public int changeNumberOfCardsInReward(int numberOfCards)
    {
        return numberOfCards + 1;
    }
    
    public void addCampfireOption(ArrayList<AbstractCampfireOption> options)
    {
        int removableCursesCount = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCurses()).size();
        
        //peace pipe can remove curses without losing HP, so no need for ritual.
        if ((removableCursesCount > 0) && !BcUtility.playerHasRelic(PeacePipe.ID))
        {
            options.add(new RitualCampfireOption(true));
        }
    }
    
    public boolean canSpawn()
    {
        return false;
    }
    
    int getInitialRemovalCost()
    {
        if (AbstractDungeon.ascensionLevel >= 16)
        {
            return InitialRemovalCostA16;
        }
        else
        {
            return InitialRemovalCost;
        }
    }
    
    int getRemovalCostIncrement()
    {
        if (AbstractDungeon.ascensionLevel >= 16)
        {
            return RemovalCostIncrementA16;
        }
        else
        {
            return RemovalCostIncrement;
        }
    }
    
    int getCurrentRemovalCost()
    {
        int currentRemovalPrice = getInitialRemovalCost() + removalCountForThisShop * getRemovalCostIncrement();
        
        if (BcUtility.playerHasRelic(SmilingMask.ID))
        {
            currentRemovalPrice -= SmilingMask.CostReduction;
        }
        
        return currentRemovalPrice;
    }
}

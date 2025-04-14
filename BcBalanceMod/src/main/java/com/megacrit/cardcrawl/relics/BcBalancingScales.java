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
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.ui.campfire.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import java.util.*;

import static bcBalanceMod.BcBalanceMod.*;

public class BcBalancingScales extends CustomRelic
{
    public static final String ID = BcBalanceMod.makeID("BcBalancingScales");
    
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("bcBalancingScales.png"));
    static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("bcBalancingScales.png"));
    
    int removalCountForThisShop;
    boolean isPlayerInAShop;
    int previousDeckSize = -1;
    
    public BcBalancingScales()
    {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }
    
    @Override
    public String getUpdatedDescription()
    {
        //return "Adds #b1 extra choice to card rewards. NL NL Note: Many of the BC Balance Mod mechanics are implemented through this relic.";
        return "Note: Many of the BC Balance Mod mechanics are implemented through this relic.";
    }
    
    @Override
    public void onRefreshHand()
    {
        boolean isCorrupted = AbstractDungeon.player.hasPower(BcCorruptionPower.POWER_ID) ||
                                      AbstractDungeon.player.hasPower(BcCorruptionPower.POWER_ID + "+");
        for (AbstractCard card : AbstractDungeon.player.hand.group)
        {
            if (card.glowColor.equals(InfernalBlade.infernalColor))
            {
                //dont change it
            }
           // else if (card.isEthereal)
            else if (card.isEthereal ||
                 (isCorrupted && (card.type == AbstractCard.CardType.SKILL)))
            {
                BcUtility.setGlowColor(card, BcUtility.corruptedGlow);
            }
            else if (card.retain && !card.selfRetain)
            {
                BcUtility.setGlowColor(card, BcUtility.retainGlowColor);
            }
            else
            {
                BcUtility.setGlowColor(card, BcUtility.normalGlowColor);
            }
        }
    }
    
    @Override
    public void onEquip()
    {
        super.onEquip();
        
        BcUtility.resetFreeYourMind();
    }
    
    @Override
    public void onEnterRoom(AbstractRoom room)
    {
        BcUtility.EtherealPlayedCount = 0;
        removalCountForThisShop = 0;
        previousDeckSize = AbstractDungeon.player.masterDeck.size();
        
        //dont want orb permanently over character's head just cause they used an orb ability once.
        // it will come back the first time they use another orb ability.
        if (AbstractDungeon.player.chosenClass != AbstractPlayer.PlayerClass.DEFECT)
        {
            AbstractDungeon.player.maxOrbs = 0;
            AbstractDungeon.player.masterMaxOrbs = 0;
        }
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
        if (card.isEthereal && (card.cost != -2))
        {
            BcUtility.EtherealPlayedCount++;
        }
    }
    
    @Override
    public void onShuffle()
    {
        applyDizzy(DizzyPower.DizzyPerShuffle);
    }
    
    public void onSpecialScryShuffle()
    {
        //this special case of applying dizzy is to preserve the intent of dizzy without punishing players for scrying.
        AbstractPlayer player = AbstractDungeon.player;
        
        if (BcUtility.isPlayerInCombat())
        {
            int dizzyToApply = DizzyPower.DizzyPerShuffle;
            //here's the magic: it's reduced by the current draw pile size.
            //dizzyToApply -= player.drawPile.size();
            
            applyDizzy(dizzyToApply);
        }
    }
    
    public void applyDizzy(int dizzyAmountToApply)
    {
        if ((dizzyAmountToApply <= 0) ||
            !BcUtility.isPlayerInCombat() ||
            (AbstractDungeon.ascensionLevel < 12))
        {
            return;
        }
        
        AbstractPlayer player = AbstractDungeon.player;
        
        int currentDizzyAmount = BcUtility.getPowerAmount(DizzyPower.POWER_ID);
        if (currentDizzyAmount + dizzyAmountToApply > DizzyPower.NauseousTheshold)
        {
            DizzyPower dizzyPower = (DizzyPower) player.getPower(DizzyPower.POWER_ID);
            dizzyPower.flash();
            AbstractDungeon.effectList.add(
                    new PowerTextEffect(
                            player.hb.cX - player.animX,
                            player.hb.cY + player.hb.height / 2.0F,
                            "Too Dizzy!",
                            dizzyPower));
            
            addToBot(new MakeTempCardInDiscardAction(new Nausea(), 1));
        }
        else
        {
            //this adds to top so that the dizzy is in place before
            // the next card is drawn, so that it can be properly reduced by it
            addToTop(new BcApplyPowerAction(new DizzyPower(player, dizzyAmountToApply)));
        }
    }
    
    @Override
    public int changeNumberOfCardsInReward(int numberOfCards)
    {
        if (AbstractDungeon.ascensionLevel >= 13)
        {
            return numberOfCards;
        }
        else
        {
            return numberOfCards + 1;
        }
//        AbstractRoom room = AbstractDungeon.getCurrRoom();
//        if ((room != null) &&
//            ((room.getClass() == TreasureRoomBoss.class) || (room.getClass() == MonsterRoomBoss.class)))
//        {
//            return numberOfCards;
//        }
//        else
//        {
//            return numberOfCards + 1;
//        }
    }
    
    public void addCampfireOption(ArrayList<AbstractCampfireOption> options)
    {
        int removableCursesCount = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCurses()).size();
        
        if ((removableCursesCount > 0) &&
            !BcUtility.playerHasRelic(PeacePipe.ID))
        {
            options.add(new RitualCampfireOption(true));
        }
    }
    
    public boolean canSpawn()
    {
        return false;
    }
    
    int getCurrentRemovalCost()
    {
        int currentRemovalPrice = BcUtility.getInitialCardRemovalCost() + removalCountForThisShop * BcUtility.getCardRemovalCostIncrement();
        
        if (BcUtility.playerHasRelic(MembershipCard.ID))
        {
            currentRemovalPrice = (int)(currentRemovalPrice * .5f);
        }
        
        if (BcUtility.playerHasRelic(Courier.ID))
        {
            currentRemovalPrice = (int)(currentRemovalPrice * .8f);
        }
        
        if (BcUtility.playerHasRelic(SmilingMask.ID))
        {
            currentRemovalPrice -= SmilingMask.CostReduction;
        }
        
        currentRemovalPrice = Math.max(1, currentRemovalPrice);
        
        return currentRemovalPrice;
    }
}

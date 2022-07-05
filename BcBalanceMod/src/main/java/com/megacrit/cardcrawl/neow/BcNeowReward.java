package com.megacrit.cardcrawl.neow;

import bcBalanceMod.*;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.screens.select.*;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BcNeowReward
{
    private static final Logger logger = LogManager.getLogger(BcNeowReward.class.getName());
    
    public String rewardDescription;
    public BcNeowRewardType rewardType;
    
    public BcNeowRewardState rewardState = BcNeowRewardState.NotStarted;
    
    public ArrayList<AbstractCard> rewardCardChoices;
    public ArrayList<AbstractCard> rewardCards = new ArrayList<AbstractCard>();
    public ArrayList<AbstractCard> cardsToRemove = new ArrayList<AbstractCard>();
    public AbstractCard rewardCurse;
    public AbstractRelic rewardRelic;
    public AbstractRelic relicToLose;
    public int strikesToUpgrade = 0;
    public int defendsToUpgrade = 0;
    public int goldToObtain = 0;
    
    public BcNeowReward(int category)
    {
        ArrayList<BcNeowRewardType> possibleRewardTypes = getRewardTypesByCategory(category);
        rewardType = possibleRewardTypes.get(BcNeowEvent.rng.random(0, possibleRewardTypes.size() - 1));
        
        String characterName = BcUtility.getCharacterName();
        AbstractCard potentialCurse = BcUtility.getRandomCard(
                AbstractCard.CardRarity.CURSE,
                AbstractCard.CardType.CURSE,
                false,
                false,
                false);
        
        ArrayList<String> extraRelicOptions = new ArrayList<>();
        switch (rewardType)
        {
            //category 1 - Visible, Boring & Modest
            case Upgrade1:
                rewardDescription = "[ #gUpgrade 1 #gCard ]";
                break;
            case Upgrade1Strike1Defend:
                strikesToUpgrade = 1;
                defendsToUpgrade = 1;
                rewardDescription = "[ #gUpgrade 1 #gStrike #gand 1 #gDefend ]";
                break;
            case Obtain100Gold:
                goldToObtain = 100;
                rewardDescription = "[ #gObtain 100 #gGold ]";
                break;
            case PickAnyCharacterCommonOrUncommon:
                rewardDescription = "[ #gPick #gany #g" + characterName + " Common #gor Uncommon #gCard ]";
                break;
            
            //category 2 - Visible & Modest
            case VisibleCommonRelic:
                extraRelicOptions.add(PrismaticShard.ID);
                rewardRelic = BcUtility.getRandomRelic(AbstractRelic.RelicTier.COMMON, extraRelicOptions);
                rewardDescription = "[ #gObtain #g( " + rewardRelic.name + " #g) ]";
                break;
            
            //category 3 - Hidden & Potent
            case Pick1Of3ColorlessCards:
                rewardCardChoices = BcUtility.getRandomCards(
                        null,
                        null,
                        true,
                        false,
                        false,
                        3,
                        null);
                rewardDescription = "[ #gPick 1 #gof 3 #gColorless #gCards ]";
                break;
            case Pick1Of3RareNativeCards:
                rewardCardChoices = BcUtility.getRandomCards(
                        AbstractCard.CardRarity.RARE,
                        null,
                        false,
                        true,
                        false,
                        3,
                        null);
                rewardDescription = "[ #gPick 1 #gof 3 #gRare #g" + characterName + " #gCards ]";
                break;
            case Pick1Of5ForeignCards:
                rewardCardChoices = BcUtility.getRandomCards(
                        AbstractCard.CardRarity.UNCOMMON,
                        null,
                        false,
                        false,
                        true,
                        5,
                        null);
                rewardDescription = "[ #gPick 1 #gof 5 #gUncommon #gForeign #gCards ]";
                break;
            case Transform2:
                rewardDescription = "[ #gTransform 2 #gCards ]";
                break;
            
            //category 4 - Visible & Potent & Cursed
            case CursedVisibleRareRelic:
                extraRelicOptions.add(Orrery.ID);
                extraRelicOptions.add(ClockworkSouvenir.ID);
                extraRelicOptions.add(MedicalKit.ID);
                extraRelicOptions.add(Toolbox.ID);
                rewardRelic = BcUtility.getRandomRelic(AbstractRelic.RelicTier.RARE, extraRelicOptions);
                rewardCurse = potentialCurse;
                rewardDescription = "[ #gObtain ( " + rewardRelic.name + " ) #rand #rbe #rCursed! #r( #l" + rewardCurse.name + " #r) ]";
                break;
            case CursedVisibleRareNativeCard:
                AbstractCard rareCard = BcUtility.getRandomCard(
                        AbstractCard.CardRarity.RARE,
                        null,
                        false,
                        true,
                        false);
                rewardCards.add(rareCard);
                rewardCurse = potentialCurse;
                rewardDescription = "[ #gObtain #g( " + rareCard.name + " #g) #rand #rbe #rCursed! #r( #l" + rewardCurse.name + " #r) ]";
                break;
            case CursedVisibleRareColorlessCard:
                AbstractCard rareColorlessCard = BcUtility.getRandomCard(
                        AbstractCard.CardRarity.RARE,
                        null,
                        true,
                        false,
                        false);
                rewardCards.add(rareColorlessCard);
                rewardCurse = potentialCurse;
                rewardDescription = "[ #gObtain #g( " + rareColorlessCard.name + " #g) #rand #rbe #rCursed! #r( #l" + rewardCurse.name + " #r) ]";
                break;
            case CursedUpgrade2Strikes2Defends:
                strikesToUpgrade = 2;
                defendsToUpgrade = 2;
                rewardCurse = potentialCurse;
                rewardDescription = "[ #gUpgrade 2 #gStrikes #gand 2 #gDefends #rand #rbe #rCursed! #r( #l" + rewardCurse.name + " #r) ]";
                break;
            
            //category 5 - Boss Swap
            case NeowsLament:
                rewardRelic = new NeowsLament();
                rewardDescription = "[ #gEnemies #gin #gyour #gnext #gthree #gcombats #ghave #g1 #gHP ]";
                break;
            case BossSwap:
                relicToLose = AbstractDungeon.player.relics.get(0);
                //extra bias towards pandora's and astrolabe
                if (BcBalanceMod.alwaysPandoras || (BcNeowEvent.rng.random(0, 10) == 0))
                {
                    rewardRelic = RelicLibrary.getRelic(PandorasBox.ID).makeCopy();
                }
                else if (BcNeowEvent.rng.random(0, 12) == 0)
                {
                    rewardRelic = RelicLibrary.getRelic(Astrolabe.ID).makeCopy();
                }
                else
                {
                    rewardRelic = BcUtility.getRandomBossRelic(true);
                }
                rewardDescription = "[ #gObtain #g( " + rewardRelic.name + " #g) #rand #rLose #r( " + relicToLose.name + " #r) ]";
                //rewardDescription = "[ #rTrade #r( " + relicToLose.name + " #r) #rfor #ga #gBoss #gRelic #g( " + rewardRelic.name + " #g) ]";
                break;
        }
    }
    
    public AbstractCard getPreviewCard()
    {
        if (rewardCards.size() == 1)
        {
            return rewardCards.get(0);
        }
        else if (rewardCurse != null)
        {
            return rewardCurse;
        }
        else
        {
            return null;
        }
    }
    
    public AbstractCard getSecondaryPreviewCard()
    {
        if ((rewardCards.size() == 1) && (rewardCurse != null))
        {
            return rewardCurse;
        }
        else
        {
            return null;
        }
    }
    
    public AbstractRelic getPreviewRelic()
    {
        return rewardRelic;
    }
    
    private ArrayList<BcNeowRewardType> getRewardTypesByCategory(int category)
    {
        ArrayList<BcNeowRewardType> neowRewardTypes = new ArrayList<BcNeowRewardType>();
        switch (category)
        {
            case 0: // Visible, Boring & Modest
                neowRewardTypes.add(BcNeowRewardType.Upgrade1);
                neowRewardTypes.add(BcNeowRewardType.Upgrade1Strike1Defend);
                neowRewardTypes.add(BcNeowRewardType.Obtain100Gold);
                neowRewardTypes.add(BcNeowRewardType.PickAnyCharacterCommonOrUncommon);
                break;
            case 1: // Visible & Modest
                neowRewardTypes.add(BcNeowRewardType.VisibleCommonRelic);
                break;
            case 2: // Hidden & Potent
                neowRewardTypes.add(BcNeowRewardType.Pick1Of3ColorlessCards);
                neowRewardTypes.add(BcNeowRewardType.Pick1Of3RareNativeCards);
                neowRewardTypes.add(BcNeowRewardType.Pick1Of5ForeignCards);
                neowRewardTypes.add(BcNeowRewardType.Transform2);
                break;
            case 3: // Visible & Potent & Cursed
                neowRewardTypes.add(BcNeowRewardType.CursedVisibleRareRelic);
                neowRewardTypes.add(BcNeowRewardType.CursedVisibleRareNativeCard);
                neowRewardTypes.add(BcNeowRewardType.CursedVisibleRareColorlessCard);
                neowRewardTypes.add(BcNeowRewardType.CursedUpgrade2Strikes2Defends);
                break;
            case 4: // Boss Swap
                neowRewardTypes.add(BcNeowRewardType.BossSwap);
                
//                //if you didn't make it to the act1 boss: neow's lament, otherwise: boss swap.
//                if ((Settings.isStandardRun() || (Settings.isEndless && AbstractDungeon.floorNum <= 1)) &&
//                            (CardCrawlGame.playerPref.getInteger(AbstractDungeon.player.chosenClass.name() + "_SPIRITS", 0) == 0))
//                {
//                    //neowRewardTypes.add(BcNeowRewardType.BossSwap);
//                    neowRewardTypes.add(BcNeowRewardType.NeowsLament);
//                }
//                else
//                {
//                    neowRewardTypes.add(BcNeowRewardType.BossSwap);
//                }
                break;
        }
        
        return neowRewardTypes;
    }
    
    public void update()
    {
        GridCardSelectScreen selectScreen = AbstractDungeon.gridSelectScreen;
        AbstractPlayer player = AbstractDungeon.player;
        
        if (rewardState == BcNeowRewardState.JustNowActivated)
        {
            //region JustNowActivated
            switch (this.rewardType)
            {
                case Transform2:
                    rewardState = BcNeowRewardState.ChoosingCards;
                    selectScreen.open(
                            player.masterDeck.getPurgeableCards(),
                            2,
                            "Transform 2",
                            false,
                            false,
                            false,
                            false);
                    break;
                case Upgrade1:
                    rewardState = BcNeowRewardState.ChoosingCards;
                    selectScreen.open(
                            player.masterDeck.getUpgradableCards(),
                            1,
                            "Upgrade 1",
                            true,
                            false,
                            false,
                            false);
                    break;
                case PickAnyCharacterCommonOrUncommon:
                    rewardState = BcNeowRewardState.ChoosingCards;
                    CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    group.group.addAll(
                            BcUtility.getAllPossibleCards(
                                    AbstractCard.CardRarity.COMMON,
                                    null,
                                    null,
                                    false,
                                    true,
                                    false,
                                    null).group);
                    group.group.addAll(
                            BcUtility.getAllPossibleCards(
                                    AbstractCard.CardRarity.UNCOMMON,
                                    null,
                                    null,
                                    false,
                                    true,
                                    false,
                                    null).group);
                    group.sortAlphabetically(true);
                    
                    selectScreen.open(
                            group,
                            1,
                            "Pick 1",
                            false,
                            false,
                            false,
                            false);
                    break;
                default:
                    rewardState = BcNeowRewardState.AwaitingReward;
                    break;
            }
            //endregion
        }
        else if (rewardState == BcNeowRewardState.ChoosingCards)
        {
            //region ChoosingCards
            if (!selectScreen.selectedCards.isEmpty())
            {
                for (AbstractCard card : selectScreen.selectedCards)
                {
                    switch (rewardType)
                    {
                        case Transform2:
                            cardsToRemove.add(card);
                            AbstractDungeon.transformCard(card, false, BcNeowEvent.rng);
                            rewardCards.add(AbstractDungeon.getTransformedCard());
                            break;
                        case Upgrade1:
                            card.upgrade();
                            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));
                            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            break;
                        case PickAnyCharacterCommonOrUncommon:
                            rewardCards.add(card);
                            break;
                    }
                }
                
                rewardState = BcNeowRewardState.AwaitingReward;
            }
            //endregion
        }
        else if (rewardState == BcNeowRewardState.AwaitingReward)
        {
            //region AwaitingReward
            if (relicToLose != null)
            {
                player.loseRelic(relicToLose.relicId);
            }
            
            for (AbstractCard cardToRemove : cardsToRemove)
            {
                player.masterDeck.removeCard(cardToRemove);
            }
            
            float centerX = (float) Settings.WIDTH / 2;
            float centerY = (float) Settings.HEIGHT / 2;
            float xOffset = (rewardCards.size() * (AbstractCard.IMG_WIDTH + 20)) / -2f;
            for (AbstractCard cardToAdd : rewardCards)
            {
                AbstractDungeon.topLevelEffects.add(
                        new ShowCardAndObtainEffect(
                                cardToAdd,
                                centerX + xOffset,
                                centerY));
                
                xOffset += AbstractCard.IMG_WIDTH + 20;
            }
            
            if (goldToObtain > 0)
            {
                CardCrawlGame.sound.play("GOLD_JINGLE");
                AbstractDungeon.player.gainGold(100);
            }
            
            if ((strikesToUpgrade > 0) || (defendsToUpgrade > 0))
            {
                //region upgrade strikes and defends
                int strikesRemaining = strikesToUpgrade;
                int defendsRemaining = defendsToUpgrade;
                
                ArrayList<AbstractCard> cardsToUpgrade = new ArrayList<AbstractCard>();
                for (AbstractCard card : AbstractDungeon.player.masterDeck.getUpgradableCards().group)
                {
                    if ((strikesRemaining > 0) && (card.cardID.startsWith("Strike_")))
                    {
                        cardsToUpgrade.add(card);
                        strikesRemaining--;
                    }
                    else if ((defendsRemaining > 0) && (card.cardID.startsWith("Defend_")))
                    {
                        cardsToUpgrade.add(card);
                        defendsRemaining--;
                    }
                }
                
                for (AbstractCard cardToUpgrade : cardsToUpgrade)
                {
                    cardToUpgrade.upgrade();
                    float x = (float) Settings.WIDTH / 2.0F + MathUtils.random(-550, 550) * Settings.scale;
                    float y = (float) Settings.HEIGHT / 2.0F + MathUtils.random(-200, 200) * Settings.scale;
                    AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(cardToUpgrade.makeStatEquivalentCopy(), x, y));
                }
                
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                //endregion
            }
            
            if (rewardRelic != null)
            {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(
                        (float) Settings.WIDTH / 2,
                        (float) Settings.HEIGHT / 2,
                        rewardRelic);
                
                BcUtility.removeRelicFromDungeon(rewardRelic.relicId);
            }
            
            if (rewardCurse != null)
            {
                AbstractDungeon.topLevelEffects.add(
                        new ShowCardAndObtainEffect(
                                rewardCurse,
                                (float) Settings.WIDTH / 2.0F,
                                (float) Settings.HEIGHT / 2.0F));
            }
            
            //this will handle giving you the reward on its own. dont have to manage it in this class.
            if (rewardCardChoices != null)
            {
                AbstractDungeon.cardRewardScreen.open(
                        rewardCardChoices,
                        null,
                        CardCrawlGame.languagePack.getUIString("CardRewardScreen").TEXT[1]);
            }
            
            CardCrawlGame.metricData.addNeowData(this.rewardType.name(), (rewardCurse != null ? rewardCurse.name : ""));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            
            rewardState = BcNeowRewardState.RewardComplete;
            //endregion
        }
        else if (rewardState == BcNeowRewardState.RewardComplete)
        {
            if ((AbstractDungeon.player.chosenClass != AbstractPlayer.PlayerClass.DEFECT) &&
                        (AbstractDungeon.player.masterMaxOrbs == 0))
            {
                for (AbstractCard cardToAdd : rewardCards)
                {
                    if (cardToAdd.color == AbstractCard.CardColor.BLUE)
                    {
                        //give player an orb slot just in case
                        AbstractDungeon.player.masterMaxOrbs = 1;
                        break;
                    }
                }
            }
        }
    }
    
    public void activate()
    {
        if (rewardState == BcNeowRewardState.NotStarted)
        {
            rewardState = BcNeowRewardState.JustNowActivated;
        }
    }
    
    public static enum BcNeowRewardType
    {
        //category 1 - Visible & Modest
        Upgrade1,
        Upgrade1Strike1Defend,
        Obtain100Gold,
        VisibleCommonRelic,
        PickAnyCharacterCommonOrUncommon,
        //category 2 - Hidden & Potent
        Pick1Of3ColorlessCards, //any rarity
        Pick1Of3RareNativeCards,
        Pick1Of5ForeignCards,
        Transform2,
        //category 3 - Curse & Visible & Potent
        CursedVisibleRareRelic,
        CursedVisibleRareNativeCard,
        CursedVisibleRareColorlessCard,
        CursedUpgrade2Strikes2Defends,
        //category 4 - Boss Swap
        NeowsLament,
        BossSwap
    }
    
    public static enum BcNeowRewardState
    {
        NotStarted,
        JustNowActivated,
        ChoosingCards,
        AwaitingReward,
        RewardComplete
    }
}
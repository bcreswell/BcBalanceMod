package com.megacrit.cardcrawl.neow;

import bcBalanceMod.*;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.screens.select.*;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
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
                null,
                false,
                BcNeowEvent.rng);
        
        switch (rewardType)
        {
            case Transform2:
                rewardDescription = "[ #gTransform 2 #gCards ]";
                break;
            case Upgrade1:
                rewardDescription = "[ #gUpgrade 1 #gCard ]";
                break;
            case Upgrade1Strike1Defend:
                strikesToUpgrade = 1;
                defendsToUpgrade = 1;
                rewardDescription = "[ #gUpgrade 1 #gStrike #gand 1 #gDefend ]";
                break;
            case Pick1Of3UncommonColorlessCards:
                rewardCardChoices = BcUtility.getRandomCardChoices(
                        AbstractCard.CardRarity.UNCOMMON,
                        null,
                        true,
                        3,
                        BcNeowEvent.rng);
                rewardDescription = "[ #gPick 1 #gof 3 #gUncommon #gColorless #gCards ]";
                break;
            case PickAnyColorfulUncommon:
                rewardDescription = "[ #gPick #gany #g" + characterName + " #gUncommon #gCard ]";
                break;
            case ObtainRareColorfulCard:
                AbstractCard rareCard = BcUtility.getRandomCard(
                        AbstractCard.CardRarity.RARE,
                        null,
                        false,
                        BcNeowEvent.rng);
                rewardCards.add(rareCard);
                rewardDescription = "[ #gObtain #ga #gRare #gCard #g( " + rareCard.name + " #g) ]";
                break;
            case Obtain100Gold:
                goldToObtain = 100;
                rewardDescription = "[ #gObtain 100 #gGold ]";
                break;
            case ObtainCommonRelic:
                rewardRelic = BcUtility.getRandomRelic(AbstractRelic.RelicTier.COMMON);
                rewardDescription = "[ #gObtain #ga #gCommon #gRelic #g( " + rewardRelic.name + " #g) ]";
                break;
            case PrismaticShard:
                rewardRelic = BcUtility.getRelicCopyFromLibrary(PrismaticShard.ID);
                rewardDescription = "[ #gObtain #g( " + rewardRelic.name + " #g) ]";
                break;
            case CursedPick1Of3RareColorfulCards:
                rewardCardChoices = BcUtility.getRandomCardChoices(
                        AbstractCard.CardRarity.RARE,
                        null,
                        false,
                        4,
                        BcNeowEvent.rng);
                rewardCurse = potentialCurse;
                rewardDescription = "[ #gPick 1 #gof 4 #gRare #g" + characterName + " #gCards #rand #rbe #rCursed! #r( #l" + rewardCurse.name + " #r) ]";
                break;
            case CursedPick1Of3RareColorlessCards:
                rewardCardChoices = BcUtility.getRandomCardChoices(
                        AbstractCard.CardRarity.RARE,
                        null,
                        false,
                        3,
                        BcNeowEvent.rng);
                rewardCurse = potentialCurse;
                rewardDescription = "[ #gPick 1 #gof 3 #gRare #gColorless #gCards #rand #rbe #rCursed! #r( #l" + rewardCurse.name + " #r) ]";
                break;
            case CursedObtainRareRelic:
                rewardRelic = BcUtility.getRandomRelic(AbstractRelic.RelicTier.RARE);
                rewardCurse = potentialCurse;
                rewardDescription = "[ #gObtain #ga #gRare #gRelic ( " + rewardRelic.name + " ) #rand #rbe #rCursed! #r( #l" + rewardCurse.name + " #r) ]";
                break;
            case CursedUpgrade2Strikes2Defends:
                strikesToUpgrade = 2;
                defendsToUpgrade = 2;
                rewardCurse = potentialCurse;
                rewardDescription = "[ #gUpgrade 2 #gStrikes #gand 2 #gDefends #rand #rbe #rCursed! #r( #l" + rewardCurse.name + " #r) ]";
                break;
            case NeowsLament:
                rewardRelic = new NeowsLament();
                rewardDescription = "[ #gEnemies #gin #gyour #gnext #gthree #gcombats #ghave #g1 #gHP ]";
                break;
            case BossSwap:
                relicToLose = AbstractDungeon.player.relics.get(0);
                rewardRelic = BcUtility.getRandomBossRelic(true);
                rewardDescription = "[ #rTrade #r( " + relicToLose.name + " #r) #rfor #ga #gBoss #gRelic #g( " + rewardRelic.name + " #g) ]";
                break;
        }
    }
    
    public AbstractCard getPreviewCard()
    {
        if (rewardCurse != null)
        {
            return rewardCurse;
        }
        else if (rewardCards.size() == 1)
        {
            return rewardCards.get(0);
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
            case 0:
                neowRewardTypes.add(BcNeowRewardType.Transform2);
                neowRewardTypes.add(BcNeowRewardType.Upgrade1);
                neowRewardTypes.add(BcNeowRewardType.Upgrade1Strike1Defend);
                neowRewardTypes.add(BcNeowRewardType.Pick1Of3UncommonColorlessCards);
                neowRewardTypes.add(BcNeowRewardType.PickAnyColorfulUncommon);
                neowRewardTypes.add(BcNeowRewardType.ObtainRareColorfulCard);
                neowRewardTypes.add(BcNeowRewardType.Obtain100Gold);
                break;
            case 1:
                neowRewardTypes.add(BcNeowRewardType.ObtainCommonRelic);
                if (MathUtils.random(0, 10) == 0)
                {
                    neowRewardTypes.add(BcNeowRewardType.PrismaticShard);
                }
                break;
            case 2:
                neowRewardTypes.add(BcNeowRewardType.CursedPick1Of3RareColorfulCards);
                neowRewardTypes.add(BcNeowRewardType.CursedPick1Of3RareColorlessCards);
                neowRewardTypes.add(BcNeowRewardType.CursedObtainRareRelic);
                neowRewardTypes.add(BcNeowRewardType.CursedUpgrade2Strikes2Defends);
                break;
            case 3:
                //if you didn't make it to the act1 boss: neow's lament, otherwise: boss swap.
                if ((Settings.isStandardRun() || (Settings.isEndless && AbstractDungeon.floorNum <= 1)) &&
                            (CardCrawlGame.playerPref.getInteger(AbstractDungeon.player.chosenClass.name() + "_SPIRITS", 0) == 0))
                {
                    neowRewardTypes.add(BcNeowRewardType.NeowsLament);
                }
                else
                {
                    neowRewardTypes.add(BcNeowRewardType.BossSwap);
                }
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
                case PickAnyColorfulUncommon:
                    rewardState = BcNeowRewardState.ChoosingCards;
                    CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    group.group.addAll(BcUtility.getAllCardsByRarity(AbstractCard.CardRarity.UNCOMMON));
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
                        case PickAnyColorfulUncommon:
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
            //nada
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
        Transform2,
        Upgrade1,
        Upgrade1Strike1Defend,
        Pick1Of3UncommonColorlessCards,
        PickAnyColorfulUncommon,
        ObtainRareColorfulCard,
        Obtain100Gold,
        ObtainCommonRelic,
        PrismaticShard, //1 in 40 chance
        CursedPick1Of3RareColorfulCards,
        CursedPick1Of3RareColorlessCards,
        CursedObtainRareRelic,
        CursedUpgrade2Strikes2Defends,
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
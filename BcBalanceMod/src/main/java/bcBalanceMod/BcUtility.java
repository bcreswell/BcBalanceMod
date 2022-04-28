package bcBalanceMod;

import basemod.*;
import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.*;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.*;

import java.util.*;

public class BcUtility
{
    //region combat queries
    public static int getCurrentFocus()
    {
        if (isPlayerInCombat())
        {
            AbstractPower focus = AbstractDungeon.player.getPower(FocusPower.POWER_ID);
            if (focus != null)
            {
                return focus.amount;
            }
        }
        
        return 0;
    }
    
    public static boolean isPlayerInCombat()
    {
        return (AbstractDungeon.player != null) &&
                       (AbstractDungeon.currMapNode != null) &&
                       (AbstractDungeon.getCurrRoom() != null) &&
                       (AbstractDungeon.getMonsters() != null) &&
                       !AbstractDungeon.getMonsters().areMonstersBasicallyDead();
    }
    
    public static int getCurrentStrength()
    {
        if (isPlayerInCombat())
        {
            AbstractPower strength = AbstractDungeon.player.getPower(StrengthPower.POWER_ID);
            if (strength != null)
            {
                return strength.amount;
            }
        }
        
        return 0;
    }
    
    public static boolean playerHasPower(String powerId)
    {
        if (isPlayerInCombat())
        {
            AbstractPower power = AbstractDungeon.player.getPower(powerId);
            return power != null;
        }
        
        return false;
    }
    
    public static int getPowerAmount(String powerId, AbstractCreature owner)
    {
        if (isPlayerInCombat())
        {
            AbstractPower power = owner.getPower(powerId);
            if (power != null)
            {
                return power.amount;
            }
        }
        
        return 0;
    }
    
    public static int getPowerAmount(String powerId)
    {
        if (isPlayerInCombat())
        {
            AbstractPower power = AbstractDungeon.player.getPower(powerId);
            if (power != null)
            {
                return power.amount;
            }
        }
        
        return 0;
    }
    
    //takes both hand space and all pending draw actions into account
    public static int getAvailableHandSpace()
    {
        if (isPlayerInCombat())
        {
            AbstractPlayer player = AbstractDungeon.player;
            int handSpace = 10 - player.hand.size();
            
            for (AbstractGameAction action : AbstractDungeon.actionManager.actions)
            {
                if (action instanceof DrawCardAction)
                {
                    DrawCardAction drawCardAction = (DrawCardAction) action;
                    handSpace -= drawCardAction.amount;
                }
            }
            
            handSpace = Math.max(0, handSpace);
            
            return handSpace;
        }
        else
        {
            return 0;
        }
    }
    
    public static boolean isPlayerInStance(String stanceId)
    {
        if (isPlayerInCombat() &&
                    (AbstractDungeon.player.stance != null) &&
                    (AbstractDungeon.player.stance.ID != null))
        {
            return AbstractDungeon.player.stance.ID.equals(stanceId);
        }
        
        return false;
    }
    
    public static AbstractMonster getRandomTarget()
    {
        return AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
//        AbstractMonster randomTarget = null;
//
//        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters)
//        {
//            if (!monster.isDeadOrEscaped())
//            {
//                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
//                break;
//            }
//        }
    }
    //endregion
    
    //region cards
    public static int getActualScryAmount(int baseScry)
    {
        if (isPlayerInCombat())
        {
            AbstractPlayer player = AbstractDungeon.player;
            AbstractPower foresightPower = player.getPower(ForesightPower.POWER_ID);
            
            if (foresightPower != null)
            {
                baseScry += foresightPower.amount;
            }
        }
        
        return baseScry;
    }
    
    public static ArrayList<AbstractCard> getRandomCardChoices(AbstractCard.CardRarity rarity, AbstractCard.CardType type, boolean isColorless, int choiceCount, Random rng)
    {
        ArrayList<AbstractCard> possibleCards = getPossibleCards(rarity, type, isColorless, choiceCount, rng);
        
        ArrayList<AbstractCard> choices = new ArrayList<>();
        for (int i = 0; i < choiceCount; i++)
        {
            choices.add(possibleCards.get(i));
        }
        
        return choices;
    }
    
    public static AbstractCard getRandomCard(AbstractCard.CardRarity rarity, AbstractCard.CardType type, boolean isColorless, Random rng)
    {
        ArrayList<AbstractCard> possibleCards = getPossibleCards(rarity, type, isColorless, 1, rng);
        if (possibleCards.size() > 0)
        {
            return possibleCards.get(0);
        }
        else
        {
            return null;
        }
    }
    
    static ArrayList<AbstractCard> getPossibleCards(AbstractCard.CardRarity rarity, AbstractCard.CardType type, boolean isColorless, int atLeast, Random rng)
    {
        if (rarity == AbstractCard.CardRarity.CURSE)
        {
            type = AbstractCard.CardType.CURSE;
            isColorless = false;
        }
        
        if (type == AbstractCard.CardType.CURSE)
        {
            rarity = AbstractCard.CardRarity.CURSE;
            isColorless = false;
        }
        
        CardGroup possibleCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        if (type == AbstractCard.CardType.STATUS)
        {
            for (AbstractCard card : CardLibrary.cards.values())
            {
                if (card.type == AbstractCard.CardType.STATUS)
                {
                    possibleCards.addToBottom(card.makeCopy());
                }
            }
        }
        else
        {
            while (possibleCards.size() < atLeast)
            {
                AbstractCard.CardRarity actualRarity = rarity;
                if (rarity == null)
                {
                    actualRarity = getRandomRarity();
                }
                
                AbstractCard.CardType actualType = type;
                if (type == null)
                {
                    actualType = getRandomType();
                }
                
                CardGroup existingGroup = null;
                switch (actualRarity)
                {
                    case COMMON:
                        existingGroup = AbstractDungeon.commonCardPool;
                        break;
                    case UNCOMMON:
                        existingGroup = AbstractDungeon.uncommonCardPool;
                        break;
                    case RARE:
                        existingGroup = AbstractDungeon.rareCardPool;
                        break;
                    case CURSE:
                        existingGroup = AbstractDungeon.curseCardPool;
                        break;
                }
                
                if (isColorless)
                {
                    existingGroup = AbstractDungeon.colorlessCardPool;
                }
                
                for (AbstractCard card : existingGroup.group)
                {
                    if ((card.rarity == actualRarity) && (card.type == actualType))
                    {
                        possibleCards.addToBottom(card.makeCopy());
                    }
                }
            }
        }
        
        possibleCards.shuffle(rng);
        return possibleCards.group;
    }
    
    public static ArrayList<AbstractCard> getAllCardsByRarity(AbstractCard.CardRarity rarity)
    {
        CardGroup group = null;
        switch (rarity)
        {
            case RARE:
                group = AbstractDungeon.rareCardPool;
                break;
            case UNCOMMON:
                group = AbstractDungeon.uncommonCardPool;
                break;
            case COMMON:
                group = AbstractDungeon.commonCardPool;
                break;
            case CURSE:
                group = AbstractDungeon.curseCardPool;
                break;
            default:
                return null;
        }
        
        ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
        for (AbstractCard card : group.group)
        {
            cards.add(card.makeCopy());
        }
        
        return cards;
    }
    
    public static AbstractCard.CardRarity getRandomRarity()
    {
        switch (AbstractDungeon.cardRng.random(0, 2))
        {
            case 0:
                return AbstractCard.CardRarity.COMMON;
            case 1:
                return AbstractCard.CardRarity.UNCOMMON;
            case 2:
            default:
                return AbstractCard.CardRarity.RARE;
        }
    }
    
    public static AbstractCard.CardType getRandomType()
    {
        switch (AbstractDungeon.cardRng.random(0, 2))
        {
            case 0:
                return AbstractCard.CardType.ATTACK;
            case 1:
                return AbstractCard.CardType.SKILL;
            case 2:
            default:
                return AbstractCard.CardType.POWER;
        }
    }
    
    public static AbstractCard getRandomCardFromDeck(String cardId, ArrayList<AbstractCard> cardList, Random rng)
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        for (AbstractCard deckCard : player.masterDeck.group)
        {
            boolean invalidChoice = false;
            for (AbstractCard existingCard : cardList)
            {
                if ((existingCard == deckCard) ||
                            ((cardId != null) && !cardId.equals(deckCard.cardID)))
                {
                    invalidChoice = true;
                    break;
                }
            }
            
            if (!invalidChoice)
            {
                return deckCard;
            }
        }
        
        return null;
    }
    
    public static void setGlowColor(AbstractCard card, Color color)
    {
        card.glowColor = color.cpy();
        if (card instanceof BcCardBase)
        {
            BcCardBase bcCard = (BcCardBase) card;
            bcCard.defaultGlow = color.cpy();
        }
    }
    //endregion
    
    //region relics
    public static AbstractRelic getPlayerRelic(String relicId)
    {
        for (AbstractRelic relic : AbstractDungeon.player.relics)
        {
            if (relic.relicId.equals(relicId))
            {
                return relic;
            }
        }
        
        return null;
    }
    
    public static boolean playerHasRelic(String relicId)
    {
        if ((AbstractDungeon.player != null) &&
                    (AbstractDungeon.currMapNode != null) &&
                    (AbstractDungeon.getCurrRoom() != null))
        {
            for (AbstractRelic relic : AbstractDungeon.player.relics)
            {
                if (relic.relicId.equals(relicId))
                {
                    return true;
                }
            }
        }
        
        
        return false;
    }
    
    public static AbstractRelic getRelicCopyFromLibrary(String relicId)
    {
        return RelicLibrary.getRelic(relicId).makeCopy();
    }
    
    public static ArrayList<AbstractRelic> getRandomRelics(int amount, AbstractRelic.RelicTier tier)
    {
        ArrayList<AbstractRelic> randomRelics = new ArrayList<AbstractRelic>();
        
        while (randomRelics.size() < amount)
        {
            AbstractRelic potentialRelic = getRandomRelic(tier);
            
            boolean isDuplicate = false;
            for (AbstractRelic existingRelic : randomRelics)
            {
                if (existingRelic.relicId.equals(potentialRelic.relicId))
                {
                    isDuplicate = true;
                }
            }
            
            if (!isDuplicate)
            {
                randomRelics.add(potentialRelic);
            }
        }
        
        return randomRelics;
    }
    
    public static AbstractRelic getRandomBossRelic(boolean excludeEvolvedStarterRelics)
    {
        String relicId = null;
        while (relicId == null)
        {
            relicId = returnRandomRelicId(AbstractRelic.RelicTier.BOSS);
            
            if (excludeEvolvedStarterRelics)
            {
                if (relicId.equals(BlackBlood.ID) ||
                            relicId.equals(RingOfTheSerpent.ID) ||
                            relicId.equals(FrozenCore.ID) ||
                            relicId.equals(HolyWater.ID))
                {
                    relicId = null;
                }
            }
        }
        
        return RelicLibrary.getRelic(relicId).makeCopy();
    }
    
    public static AbstractRelic getRandomRelic(AbstractRelic.RelicTier tier)
    {
        return RelicLibrary.getRelic(returnRandomRelicId(tier)).makeCopy();
    }
    
    public static void removeRelicFromDungeon(String relicKey)
    {
        AbstractDungeon.commonRelicPool.remove(relicKey);
        AbstractDungeon.uncommonRelicPool.remove(relicKey);
        AbstractDungeon.rareRelicPool.remove(relicKey);
        AbstractDungeon.shopRelicPool.remove(relicKey);
        AbstractDungeon.bossRelicPool.remove(relicKey);
    }
    
    static String returnRandomRelicId(AbstractRelic.RelicTier tier)
    {
        String retVal = null;
        while (retVal == null)
        {
            switch (tier)
            {
                case COMMON:
                    if (AbstractDungeon.commonRelicPool.isEmpty())
                    {
                        retVal = returnRandomRelicId(AbstractRelic.RelicTier.UNCOMMON);
                    }
                    else
                    {
                        retVal = getRandomRelicIdFromPool(AbstractDungeon.commonRelicPool);
                    }
                    break;
                case UNCOMMON:
                    if (AbstractDungeon.uncommonRelicPool.isEmpty())
                    {
                        retVal = returnRandomRelicId(AbstractRelic.RelicTier.RARE);
                    }
                    else
                    {
                        retVal = getRandomRelicIdFromPool(AbstractDungeon.uncommonRelicPool);
                    }
                    break;
                case RARE:
                    if (AbstractDungeon.rareRelicPool.isEmpty())
                    {
                        retVal = "Circlet";
                    }
                    else
                    {
                        retVal = getRandomRelicIdFromPool(AbstractDungeon.rareRelicPool);
                    }
                    break;
                case SHOP:
                    if (AbstractDungeon.shopRelicPool.isEmpty())
                    {
                        retVal = returnRandomRelicId(AbstractRelic.RelicTier.UNCOMMON);
                    }
                    else
                    {
                        retVal = getRandomRelicIdFromPool(AbstractDungeon.shopRelicPool);
                    }
                    break;
                case BOSS:
                    if (AbstractDungeon.bossRelicPool.isEmpty())
                    {
                        retVal = "Red Circlet";
                    }
                    else
                    {
                        retVal = getRandomRelicIdFromPool(AbstractDungeon.bossRelicPool);
                    }
                    break;
                default:
            }
            
            if (!RelicLibrary.getRelic(retVal).canSpawn())
            {
                retVal = null;
            }
        }
        
        return retVal;
    }
    
    static String getRandomRelicIdFromPool(ArrayList<String> relicPool)
    {
        if (relicPool.isEmpty())
        {
            return null;
        }
        else
        {
            return relicPool.get(AbstractDungeon.relicRng.random(0, relicPool.size() - 1));
        }
    }
    //endregion
    
    //region string, int, float utility
    public static boolean isNullEmptyOrWhiteSpace(String string)
    {
        return (string == null) || (string.trim().isEmpty());
    }
    
    public static int clamp(int min, int value, int max)
    {
        return Math.max(min, Math.min(max, value));
    }
    //endregion
    
    public static String getCharacterName()
    {
        switch (AbstractDungeon.player.chosenClass)
        {
            case IRONCLAD:
                return "Ironclad";
            case THE_SILENT:
                return "Silent";
            case DEFECT:
                return "Defect";
            case WATCHER:
                return "Watcher";
            default:
                return AbstractDungeon.player.name;
        }
    }
    
    //region system fixes
    public static void leaveContollerMode()
    {
        ReflectionHacks.RMethod m = ReflectionHacks.privateMethod(InputHelper.class, "leaveControllerMode");
        m.invoke(null);
    }
    
    public static void popuplateLocalizationPlaceholders()
    {
        String modName = BcBalanceMod.getModID();
        Map localizationStrings = ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "cards");
        localizationStrings.put(modName + ":temp", new CardStrings());
        
        for (AbstractCard card : CardLibrary.getAllCards())
        {
            if (card.cardID.startsWith(modName))
            {
                localizationStrings.put(card.cardID, new CardStrings());
            }
        }
        
        //ReflectionHacks.setPrivateStaticFinal(LocalizedStrings.class, typeMap, localizationStrings);
    }
    //endregion
}

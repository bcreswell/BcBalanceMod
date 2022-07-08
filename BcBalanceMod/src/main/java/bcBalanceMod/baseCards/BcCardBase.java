package bcBalanceMod.baseCards;

import basemod.ReflectionHacks;
import bcBalanceMod.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.*;

import java.util.*;

import static bcBalanceMod.BcBalanceMod.makeCardPath;

public abstract class BcCardBase extends AbstractCard
{
    static String noneImgPath = makeCardPath("none.png");
    protected CardStrings cardStrings;
    public Color defaultGlow = null;
    static final String tempId = BcBalanceMod.makeID("temp");
    public boolean isManuallyEthereal;
    
    public BcCardBase()
    {
        super(tempId, tempId, "status/beta", -2, tempId, CardType.STATUS, CardColor.COLORLESS, CardRarity.BASIC, CardTarget.SELF);
        
        cardID = getId();
        originalName = getDisplayName();
        if (originalName == null)
        {
            cardStrings = CardCrawlGame.languagePack.getCardStrings(cardID);
            if (cardStrings != null)
            {
                originalName = cardStrings.NAME;
            }
            else
            {
                originalName = "(unnamed)";
            }
        }
        
        name = originalName;
        cost = getCost();
        costForTurn = cost;
        type = getCardType();
        color = getCardColor();
        rarity = getCardRarity();
        target = getCardTarget();
        
        damageType = getDamageType();
        damageTypeForTurn = damageType;
        
        baseDamage = getDamage();
        damage = baseDamage;
        
        baseBlock = getBlock();
        block = baseBlock;
        
        baseMagicNumber = getMagicNumber();
        magicNumber = baseMagicNumber;
        
        exhaust = getExhaust();
        selfRetain = getRetain();
        isEthereal = getEthereal();
        isInnate = getInnate();
        
        isMultiDamage = isAoeAttack();
        
        String imgPath = getImagePath();
        String betaImgPath = getBetaImagePath();
        
        //base game paths refer to a name in an atlas while custom paths refer to a path to a .png file
        if (!imgPath.endsWith(".png"))
        {
            assetUrl = imgPath;
            TextureAtlas cardAtlas = ReflectionHacks.getPrivateStatic(AbstractCard.class, "cardAtlas");
            TextureAtlas oldCardAtlas = ReflectionHacks.getPrivateStatic(AbstractCard.class, "oldCardAtlas");
            portrait = cardAtlas.findRegion(assetUrl);
            jokePortrait = oldCardAtlas.findRegion(assetUrl);
            
            if (portrait == null)
            {
                if (jokePortrait != null)
                {
                    portrait = jokePortrait;
                }
                else
                {
                    portrait = cardAtlas.findRegion("status/beta");
                }
            }
        }
        else
        {
            String fullImagePath = makeCardPath(imgPath);
            Texture cardTexture = BcUtility.getTexture(fullImagePath);
            
            portrait = new TextureAtlas.AtlasRegion(
                    cardTexture,
                    0,
                    0,
                    cardTexture.getWidth(),
                    cardTexture.getHeight());
            
            int endingIndex = fullImagePath.lastIndexOf(".");
            String largePortraitPath = fullImagePath.substring(0, endingIndex) + "_p" + fullImagePath.substring(endingIndex);
            
            portraitTexture = BcUtility.getTexture(largePortraitPath);
    
            if (betaImgPath != null)
            {
                fullImagePath = makeCardPath(betaImgPath);
                cardTexture = BcUtility.getTexture(fullImagePath);
    
                jokePortrait = new TextureAtlas.AtlasRegion(
                        cardTexture,
                        0,
                        0,
                        cardTexture.getWidth(),
                        cardTexture.getHeight());
    
                endingIndex = fullImagePath.lastIndexOf(".");
                largePortraitPath = fullImagePath.substring(0, endingIndex) + "_p" + fullImagePath.substring(endingIndex);
    
                betaPortraitTexture = BcUtility.getTexture(largePortraitPath);
            }
        }
        
        onInitialized();
        
        rawDescription = getFullDescription();
        initializeTitle();
        initializeDescription();
        
        ReflectionHacks.RMethod createCardImage = ReflectionHacks.privateMethod(AbstractCard.class, "createCardImage");
        createCardImage.invoke(this);
    }
    
    @Override
    public void update()
    {
        super.update();
        //doing this manually below instead of using these fields
        showEvokeValue = false;
        showEvokeOrbCount = 0;
        
        AbstractPlayer player = AbstractDungeon.player;
        if (BcUtility.isPlayerInCombat() &&
                    (this == player.hoveredCard) &&
                    player.isHoveringDropZone &&
                    (player.orbs != null))
        {
            int channeledOrbCount = getChanneledOrbCount();
            channeledOrbCount += getExtraOrbChannelCount();
            
            int filledOrbCount = player.filledOrbCount();
            int currentOrbCount = player.orbs.size();
            int maxOrbCount = player.maxOrbs;
            
            int createdOrbSlots = getNumberOfOrbsSlotsCreated();
            createdOrbSlots = BcUtility.clamp(maxOrbCount - currentOrbCount, createdOrbSlots, 10);
            
            int evokedOrbsCount = (channeledOrbCount + filledOrbCount) - (currentOrbCount + createdOrbSlots);
            evokedOrbsCount = BcUtility.clamp(0, evokedOrbsCount, 10);
            evokedOrbsCount += getNumberOfOrbsEvokedDirectly();
            evokedOrbsCount = BcUtility.clamp(0, evokedOrbsCount, 10);
            
            int evokeTimes = getEvokeIterations();
            if ((evokeTimes < 1) && (evokedOrbsCount > 0))
            {
                evokeTimes = 1;
            }
            
            int i = 0;
            for (AbstractOrb orb : AbstractDungeon.player.orbs)
            {
                if ((i < evokedOrbsCount) &&
                            !(orb instanceof EmptyOrbSlot))
                {
                    orb.showEvokeValue();
                    orb.evokeTimes = evokeTimes;
                }
                
                i++;
            }
        }
    }
    
    int getExtraOrbChannelCount()
    {
        int extraCount = 0;
        if (BcUtility.isPlayerInCombat())
        {
            if (type == CardType.POWER)
            {
                return BcUtility.getPowerAmount(StormPower.POWER_ID);
            }
            else if (type == CardType.ATTACK)
            {
                //just assuming here that the static discharge will deal unblocked attack to whichever target.
                return BcUtility.getPowerAmount(DecryptionDancePower.POWER_ID) + BcUtility.getPowerAmount(StaticDischargePower.POWER_ID);
            }
        }
        
        return extraCount;
    }
    
    protected void onInitialized()
    {
    }
    
    public void atTurnStartPreDraw()
    {
        rawDescription = getFullDescription() + getFormatedExtraDescription(null);
        initializeDescription();
    }
    
    public void applyPowers()
    {
        super.applyPowers();
        
        rawDescription = getFullDescription() + getFormatedExtraDescription(null);
        initializeDescription();
    }
    
    public void onMoveToDiscard()
    {
        rawDescription = getFullDescription();
        initializeDescription();
    }
    
    @Override
    public void calculateCardDamage(AbstractMonster monster)
    {
        super.calculateCardDamage(monster);
        
        //this is called right before card description is rendered.
        // targeting and modified damage is cleared immediately after, so description update has to happen here.
        rawDescription = getFullDescription() + getFormatedExtraDescription(monster);
        initializeDescription();
    }
    
    //region card parameters
    public String getDisplayName()
    {
        return null;
    }
    
    public abstract String getImagePath();
    
    public String getBetaImagePath()
    {
        return null;
    }
    
    //this should be sealed and just get the ID based on the mod name + class name
    public abstract String getId();
    
    public abstract CardType getCardType();
    
    public CardColor getCardColor()
    {
        String imgPath = getImagePath().toLowerCase(Locale.ROOT);
        if (imgPath.startsWith("red"))
        {
            return CardColor.RED;
        }
        else if (imgPath.startsWith("green"))
        {
            return CardColor.GREEN;
        }
        else if (imgPath.startsWith("blue"))
        {
            return CardColor.BLUE;
        }
        else if (imgPath.startsWith("purple"))
        {
            return CardColor.PURPLE;
        }
        else if (imgPath.startsWith("curse"))
        {
            return CardColor.CURSE;
        }
        else if (imgPath.startsWith("status"))
        {
            return CardColor.COLORLESS;
        }
        else
        {
            return CardColor.COLORLESS;
        }
    }
    
    public abstract CardRarity getCardRarity();
    
    public abstract CardTarget getCardTarget();
    
    //x-cost is -1, unplayable is -2
    public abstract int getCost();
    
    public DamageInfo.DamageType getDamageType()
    {
        return DamageInfo.DamageType.NORMAL;
    }
    
    public int getBlock()
    {
        return -1;
    }
    
    public int getDamage()
    {
        return -1;
    }
    
    public static boolean isARetrieveCard(AbstractCard card)
    {
        if (card instanceof BcCardBase)
        {
            return ((BcCardBase) card).isARetrieveCard();
        }
        else
        {
            return false;
        }
    }
    
    public boolean isARetrieveCard()
    {
        return false;
    }
    
    public boolean isAoeAttack()
    {
        return false;
    }
    
    public int getMagicNumber()
    {
        return -1;
    }
    
    public boolean getExhaust()
    {
        return false;
    }
    
    public boolean getRetain()
    {
        return false;
    }
    
    public boolean getInnate()
    {
        return false;
    }
    
    public boolean getEthereal()
    {
        return false;
    }
    
    public int getChanneledOrbCount()
    {
        return 0;
    }
    
    public int getNumberOfOrbsSlotsCreated()
    {
        return 0;
    }
    
    public int getNumberOfOrbsEvokedDirectly()
    {
        return 0;
    }
    
    //dualcast is 2 for example
    public int getEvokeIterations()
    {
        //this is just a reasonable default value. it is overriden in dualcast/multicast
        return (getNumberOfOrbsEvokedDirectly() > 0) ? 1 : 0;
    }
    
    public String getFootnote()
    {
        return null;
    }
    
    public abstract String getBaseDescription();
    
    public String getFullDescription()
    {
        String baseDescription = getBaseDescription();
        String footnote = getFootnote();
        
        //build a description based on their properties + the base description.
        String fullDescription = baseDescription;
        String line1 = "";
        
        if (getInnate())
        {
            line1 += "Innate. ";
        }
        
        if (getRetain())
        {
            line1 += "Retain. ";
        }
        
        if (getEthereal())
        {
            line1 += "Ethereal. ";
        }
        
        if (!line1.equals(""))
        {
            line1 += "NL ";
        }
        
        fullDescription = line1 + fullDescription;
        
        if (getExhaust())
        {
            fullDescription += " NL Exhaust.";
        }
        
        if (footnote != null)
        {
            fullDescription += " NL NL (" + footnote + ")";
        }
        
        return fullDescription;
    }
    
    public String getTemporaryExtraDescription(AbstractMonster monster)
    {
        return null;
    }
    
    String getFormatedExtraDescription(AbstractMonster monster)
    {
        String extra = getTemporaryExtraDescription(monster);
        if ((extra == null) || (extra.length() == 0))
        {
            return "";
        }
        else
        {
            return " NL (" + extra + ")";
        }
    }
    
    public AbstractCard makeCopy()
    {
        try
        {
            return this.getClass().newInstance();
        }
        catch (IllegalAccessException | InstantiationException var2)
        {
            throw new RuntimeException("BaseMod failed to auto-generate makeCopy for card: " + this.cardID);
        }
    }
    //endregion
    
    public final void upgrade()
    {
        if (!upgraded)
        {
            int originalCost = getCost();
            int originalDamage = getDamage();
            int originalBlock = getBlock();
            int originalMagicNumber = getMagicNumber();
            
            //set upgraded to true
            upgradeName();
            
            int newCost = getCost();
            int newDamage = getDamage();
            int newBlock = getBlock();
            int newMagicNumber = getMagicNumber();
            
            if (originalCost != newCost)
            {
                upgradeBaseCost(newCost);
            }
            
            if (originalDamage != newDamage)
            {
                baseDamage = newDamage;
                damage = newDamage;
                upgradedDamage = true;
            }
            
            if (originalBlock != newBlock)
            {
                baseBlock = newBlock;
                block = newBlock;
                upgradedBlock = true;
            }
            
            if (originalMagicNumber != newMagicNumber)
            {
                baseMagicNumber = newMagicNumber;
                magicNumber = newMagicNumber;
                upgradedMagicNumber = true;
            }
            
            exhaust = getExhaust();
            selfRetain = getRetain();
            //if a card was made ethereal during combat and then upgraded during combat, it should stay ethereal
            isEthereal = isManuallyEthereal || getEthereal();
            isInnate = getInnate();
            
            onUpgraded();
            
            rawDescription = getFullDescription();
            initializeDescription();
        }
    }
    
    protected void onUpgraded()
    {
    
    }
    
    public boolean isGlowingGold()
    {
        return false;
    }
    
    public final void triggerOnGlowCheck()
    {
        if (defaultGlow == null)
        {
            defaultGlow = glowColor;
        }
        glowColor = isGlowingGold() ? AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy() : defaultGlow;
    }
    
    @Override
    public AbstractCard makeStatEquivalentCopy()
    {
        BcCardBase card = (BcCardBase) super.makeStatEquivalentCopy();
        card.isManuallyEthereal = isManuallyEthereal;
        
        return card;
    }
}

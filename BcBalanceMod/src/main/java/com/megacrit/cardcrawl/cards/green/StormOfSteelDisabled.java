//package com.megacrit.cardcrawl.cards.green;
//
//import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
//import com.megacrit.cardcrawl.actions.common.*;
//import com.megacrit.cardcrawl.cards.tempCards.Shiv;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import com.megacrit.cardcrawl.powers.*;
//
//import java.security.acl.*;

//public class StormOfSteel extends BcAttackCardBase
//{
//    public static final String ID = "Storm of Steel";
//
//    //region card parameters
//    @Override
//    protected void onInitialized()
//    {
//        cardsToPreview = new Shiv();
//    }
//
//    @Override
//    public String getImagePath()
//    {
//        return "green/stormOfSteel.png";
//    }
//
//    @Override
//    public int getCost()
//    {
//        return 3;
//    }
//
//    @Override
//    public String getId()
//    {
//        return ID;
//    }
//
//    @Override
//    public CardRarity getCardRarity()
//    {
//        return CardRarity.RARE;
//    }
//
//    @Override
//    public int getMagicNumber()
//    {
//        return !upgraded ? 6 : 9;
//    }
//
//    @Override
//    public String getBaseDescription()
//    {
//        return "Create !M! *Hidden Shivs. NL Launch ALL remaining NL *Hidden Shivs at random targets.";
//    }
//
//    @Override
//    public String getTemporaryExtraDescription(AbstractMonster monster)
//    {
//        int shivCount = BcUtility.getPowerAmount(HiddenShivPower.POWER_ID) + getMagicNumber();
//        return "total: " + shivCount + " shivs";
//    }
//
//    @Override
//    public int getDamage()
//    {
//        return 0;
//    }
//
//    @Override
//    public boolean isAoeAttack()
//    {
//        return false;
//    }
//
//    @Override
//    public CardTarget getCardTarget()
//    {
//        return CardTarget.NONE;
//    }
//    //endregion
//
//    public void use(AbstractPlayer player, AbstractMonster monster)
//    {
//        addToBot(new BcApplyPowerAction(new HiddenShivPower(player, magicNumber)));
//        addToBot(new StormOfSteelAction(player));
//    }
//}

package com.megacrit.cardcrawl.monsters.ending;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Iterator;

public class SpireSpear extends AbstractMonster
{
    public static final String ID = "SpireSpear";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private int moveCount = 0;
    private static final byte BURN_STRIKE = 1;
    private static final byte PIERCER = 2;
    private static final byte SKEWER = 3;
    private static final int BURN_STRIKE_COUNT = 2;
    private int skewerCount;
    
    public SpireSpear()
    {
        super(NAME, "SpireSpear", 160, 0.0F, -15.0F, 380.0F, 290.0F, (String) null, 70.0F, 10.0F);
        type = AbstractMonster.EnemyType.ELITE;
        loadAnimation("images/monsters/theEnding/spear/skeleton.atlas", "images/monsters/theEnding/spear/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = state.setAnimation(0, (String) "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.7F);
        if (AbstractDungeon.ascensionLevel >= 8)
        {
            //setHp(180);
            setHp(200);
        }
        else
        {
            setHp(160);
        }
        
        if (AbstractDungeon.ascensionLevel >= 3)
        {
            skewerCount = 4;
            damage.add(new DamageInfo(this, 6));
            damage.add(new DamageInfo(this, 10));
        }
        else
        {
            skewerCount = 3;
            damage.add(new DamageInfo(this, 5));
            damage.add(new DamageInfo(this, 10));
        }
        
    }
    
    public void usePreBattleAction()
    {
        if (AbstractDungeon.ascensionLevel >= 18)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 2)));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 1)));
        }
        
    }
    
    public void takeTurn()
    {
        int i;
label36:
        switch (nextMove)
        {
            case 1:
                for (i = 0; i < 2; ++i)
                {
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.15F));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(0), AbstractGameAction.AttackEffect.FIRE));
                }
                
                if (AbstractDungeon.ascensionLevel >= 18)
                {
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Burn(), 2, false, true));
                }
                else
                {
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Burn(), 2));
                }
                break;
            case 2:
                Iterator var3 = AbstractDungeon.getMonsters().monsters.iterator();
                
                while (true)
                {
                    if (!var3.hasNext())
                    {
                        break label36;
                    }
                    
                    AbstractMonster m = (AbstractMonster) var3.next();
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, 2), 2));
                }
            case 3:
                for (i = 0; i < skewerCount; ++i)
                {
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.05F));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(1), AbstractGameAction.AttackEffect.SLASH_DIAGONAL, true));
                }
        }
        
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    protected void getMove(int num)
    {
        switch (moveCount % 3)
        {
            case 0:
                if (!lastMove((byte) 1))
                {
                    setMove((byte) 1, AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo) damage.get(0)).base, 2, true);
                }
                else
                {
                    setMove((byte) 2, AbstractMonster.Intent.BUFF);
                }
                break;
            case 1:
                setMove((byte) 3, AbstractMonster.Intent.ATTACK, ((DamageInfo) damage.get(1)).base, skewerCount, true);
                break;
            default:
                if (AbstractDungeon.aiRng.randomBoolean())
                {
                    setMove((byte) 2, AbstractMonster.Intent.BUFF);
                }
                else
                {
                    setMove((byte) 1, AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo) damage.get(0)).base, 2, true);
                }
        }
        
        ++moveCount;
    }
    
    public void changeState(String key)
    {
        AnimationState.TrackEntry e = null;
        byte var4 = -1;
        switch (key.hashCode())
        {
            case -273028538:
                if (key.equals("SLOW_ATTACK"))
                {
                    var4 = 0;
                }
                break;
            case 1941037640:
                if (key.equals("ATTACK"))
                {
                    var4 = 1;
                }
        }
        
        switch (var4)
        {
            case 0:
                state.setAnimation(0, (String) "Attack_1", false);
                e = state.addAnimation(0, (String) "Idle", true, 0.0F);
                e.setTimeScale(0.5F);
                break;
            case 1:
                state.setAnimation(0, (String) "Attack_2", false);
                e = state.addAnimation(0, (String) "Idle", true, 0.0F);
                e.setTimeScale(0.7F);
        }
        
    }
    
    public void damage(DamageInfo info)
    {
        super.damage(info);
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0)
        {
            state.setAnimation(0, (String) "Hit", false);
            AnimationState.TrackEntry e = state.addAnimation(0, (String) "Idle", true, 0.0F);
            e.setTimeScale(0.7F);
        }
        
    }
    
    public void die()
    {
        super.die();
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        
        while (var1.hasNext())
        {
            AbstractMonster m = (AbstractMonster) var1.next();
            if (!m.isDead && !m.isDying)
            {
                if (AbstractDungeon.player.hasPower("Surrounded"))
                {
                    AbstractDungeon.player.flipHorizontal = m.drawX < AbstractDungeon.player.drawX;
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, "Surrounded"));
                }
                
                if (m.hasPower("BackAttack"))
                {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, m, "BackAttack"));
                }
            }
        }
    }
    
    static
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SpireSpear");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}

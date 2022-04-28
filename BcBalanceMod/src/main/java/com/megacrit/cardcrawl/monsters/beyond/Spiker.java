package com.megacrit.cardcrawl.monsters.beyond;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;

public class Spiker extends AbstractMonster
{
    public static final String ID = "Spiker";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final String ENCOUNTER_NAME = "Ancient Shapes";
    private static final int HP_MIN = 42;
    private static final int HP_MAX = 56;
    private static final int A_2_HP_MIN = 44;
    private static final int A_2_HP_MAX = 60;
    private static final float HB_X = -8.0F;
    private static final float HB_Y = -10.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 150.0F;
    private static final int STARTING_THORNS = 3;
    private static final int A_2_STARTING_THORNS = 4;
    private int startingThorns;
    private static final byte ATTACK = 1;
    private static final int ATTACK_DMG = 7;
    private static final int A_2_ATTACK_DMG = 9;
    private int attackDmg;
    private static final byte BUFF_THORNS = 2;
    private static final int BUFF_AMT = 2;
    private int thornsCount = 0;
    
    public Spiker(float x, float y)
    {
        super(NAME, "Spiker", 56, -8.0F, -10.0F, 150.0F, 150.0F, (String) null, x, y + 10.0F);
        loadAnimation("images/monsters/theForest/spiker/skeleton.atlas", "images/monsters/theForest/spiker/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = state.setAnimation(0, (String) "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        if (AbstractDungeon.ascensionLevel >= 7)
        {
            //setHp(44, 60);
            setHp(56, 68);
        }
        else
        {
            setHp(42, 56);
        }
        
        startingThorns = 3;
        attackDmg = 7;
        
        if (AbstractDungeon.ascensionLevel >= 2)
        {
            startingThorns = 4;
            attackDmg = 9;
        }
        
        if (AbstractDungeon.ascensionLevel >= 17)
        {
            startingThorns = 7;
            attackDmg = 12;
        }
        
        damage.add(new DamageInfo(this, attackDmg));
    }
    
    public void usePreBattleAction()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThornsPower(this, startingThorns)));
    }
    
    public void takeTurn()
    {
        switch (nextMove)
        {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                break;
            case 2:
                ++thornsCount;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThornsPower(this, 2), 2));
        }
        
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    protected void getMove(int num)
    {
        if (thornsCount >= 2)
        {
            setMove((byte) 1, AbstractMonster.Intent.ATTACK, (damage.get(0)).base);
        }
        else if (num < 50 && !lastMove((byte) 1))
        {
            setMove((byte) 1, AbstractMonster.Intent.ATTACK, (damage.get(0)).base);
        }
        else
        {
            setMove((byte) 2, AbstractMonster.Intent.BUFF);
        }
    }
    
    static
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Spiker");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}

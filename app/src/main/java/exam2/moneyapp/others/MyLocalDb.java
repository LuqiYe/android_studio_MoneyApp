package exam2.moneyapp.others;


import java.util.ArrayList;
import java.util.List;

import exam2.moneyapp.dto.AccDto;
import io.realm.Realm;
import io.realm.exceptions.RealmException;
import exam2.moneyapp.R;
public class MyLocalDb {

    public MyLocalDb() {
    }

    public List<AccDto> getAccounts() {
        try {

            final Realm sd = Realm.getDefaultInstance();
            List<AccDto> accountBean = sd.where(AccDto.class).findAll();
            return accountBean;
        } catch (RealmException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<AccDto> SerachAcc(String from,String to) {
        try {
            final long f = Const.getTimeMillies(from);
            final long t = Const.getTimeMillies(to);


            final Realm sd = Realm.getDefaultInstance();
            final List<AccDto> accountBean = sd.where(AccDto.class).findAll();
            final List<AccDto> accountBean1 =  new ArrayList<>();
            sd.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    for(AccDto accountBean2:accountBean){
                        long m = Const.getTimeMillies(accountBean2.getDate());
                        if(m>=f && m<=t){
                            accountBean1.add(accountBean2);
                        }
                    }
                }
            });


            return accountBean1;
        } catch (RealmException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
        return null;
    }

    public double getBalance() {
        final double[] add = {0};
        final double[] minus = {0};
        double total = 0;
        try {
            final Realm sd = Realm.getDefaultInstance();
            final List<AccDto> accountBean = sd.where(AccDto.class).findAll();
            sd.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    for(AccDto accountBean2:accountBean){
                        if(accountBean2.getType().equals(Const.added)){
                            add[0] +=Double.parseDouble(accountBean2.getAmount());
                        }
                        else{
                            minus[0] +=Double.parseDouble(accountBean2.getAmount());
                        }
                    }
                }
            });


        } catch (RealmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }

        total = add[0] - minus[0];

        return total;
    }
    public AccDto getAccountById(String Id) {
        try {

            final Realm sd = Realm.getDefaultInstance();
            AccDto accountBean = sd.where(AccDto.class).equalTo(Const.transId, Id).findFirst();
            return accountBean;
        } catch (RealmException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
        return null;
    }
    public void saveAccount(final AccDto accountBean) {
        try {
            final Realm sd = Realm.getDefaultInstance();
            sd.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(accountBean);
                }
            });
        } catch (RealmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }

    }
    public void saveAccounts(final List<AccDto> l1) {
        try {
            final Realm sd = Realm.getDefaultInstance();
            sd.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    for (AccDto accountBean : l1) {
                        realm.copyToRealmOrUpdate(accountBean);

                    }
                }
            });
        } catch (RealmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }

    }
}

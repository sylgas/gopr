package com.agh.gopr.app.ioc;

import com.agh.gopr.app.common.Preferences_;
import com.agh.gopr.app.database.dao.GroupDao;
import com.agh.gopr.app.database.dao.MessageDao;
import com.agh.gopr.app.database.dao.NoteDao;
import com.agh.gopr.app.database.dao.PositionDao;
import com.agh.gopr.app.database.dao.UserDao;
import com.agh.gopr.app.database.dao.UserGroupDao;
import com.agh.gopr.app.ioc.provider.MapFragmentProvider;
import com.agh.gopr.app.ioc.provider.PreferencesProvider;
import com.agh.gopr.app.ioc.provider.RequestServiceProvider;
import com.agh.gopr.app.ioc.provider.dao.GroupDaoProvider;
import com.agh.gopr.app.ioc.provider.dao.MessageDaoProvider;
import com.agh.gopr.app.ioc.provider.dao.NoteDaoProvider;
import com.agh.gopr.app.ioc.provider.dao.PositionDaoProvider;
import com.agh.gopr.app.ioc.provider.dao.UserDaoProvider;
import com.agh.gopr.app.ioc.provider.dao.UserGroupDaoProvider;
import com.agh.gopr.app.service.rest.RequestService;
import com.agh.gopr.app.service.rest.service.GpsGetPositionsService;
import com.agh.gopr.app.service.rest.service.GpsPostPositionsService;
import com.agh.gopr.app.service.rest.service.IMethodService;
import com.agh.gopr.app.ui.fragment.MapFragment;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.multibindings.Multibinder;

public class GeoSUPModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(RequestService.class).toProvider(RequestServiceProvider.class);
        binder.bind(MapFragment.class).toProvider(MapFragmentProvider.class);
        binder.bind(Preferences_.class).toProvider(PreferencesProvider.class);
        bindMethodServices(binder);
        bindDaos(binder);
    }

    private void bindDaos(Binder binder) {
        binder.bind(GroupDao.class).toProvider(GroupDaoProvider.class);
        binder.bind(MessageDao.class).toProvider(MessageDaoProvider.class);
        binder.bind(NoteDao.class).toProvider(NoteDaoProvider.class);
        binder.bind(PositionDao.class).toProvider(PositionDaoProvider.class);
        binder.bind(UserDao.class).toProvider(UserDaoProvider.class);
        binder.bind(UserGroupDao.class).toProvider(UserGroupDaoProvider.class);
    }

    private void bindMethodServices(Binder binder) {
        Multibinder<IMethodService> methodServices = Multibinder.newSetBinder(binder, IMethodService.class);
        methodServices.addBinding().to(GpsGetPositionsService.class);
        methodServices.addBinding().to(GpsPostPositionsService.class);
    }
}

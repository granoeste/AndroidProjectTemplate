package yuki.android.ormasample.crosscut.bus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * OttoやEventBusといったPub/Subメッセージングの仕組みをRxで実現するクラス.
 * <pre> {@code
 * private CompositeSubscription mCompositeSubscription;
 *
 * @Override
 * protected void onResume() {
 *     super.onResume();
 *
 *     mCompositeSubscription = new CompositeSubscription();
 *     mCompositeSubscription.add(
 *         RxBusProvider.getInstance()
 *             .toObservable()
 *             .observeOn(AndroidSchedulers.mainThread())
 *             .subscribe(o -> {
 *                 if (o instanceof SomeEvent) {
 *                     // catch it!
 *                 }
 *             })
 *     );
 * }
 *
 * @Override
 * protected void onPause() {
 *     super.onPause();
 *
 *     // Subscription解除
 *     mCompositeSubscription.unsubscribe();
 * }
 * } </pre>
 * Deprecate Otto in favor of RxJava - https://github.com/square/otto/pull/181
 */
public class BusStream {

    private final Subject<Object, Object> STREAM =
            new SerializedSubject<>(PublishSubject.create());

    public BusStream() {
    }

    public void send(Object o) {
        STREAM.onNext(o);
    }

    public Observable<Object> toObservable() {
        return STREAM;
    }

    public boolean hasObservers() {
        return STREAM.hasObservers();
    }
}

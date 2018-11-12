package com.example.android.recyclerview;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

// This is instantiated by the Activity or Fragment calling the ViewModelProvider factory method.
public class MyViewModel extends ViewModel {
    private LiveData<PagedList<MyItem>> myList;
    private int pageSize = 40;

    public LiveData<PagedList<MyItem>> getMyList() {
        if (myList == null) {
            myList = new LivePagedListBuilder<Integer, MyItem>(
                    new MyDataSourceFactory(),
                    pageSize
            ).build();
        }
        return myList;
    }

    class MyDataSource extends PositionalDataSource<MyItem> {

        private int mPageSize;
        private int mTotalCount = 600;

        /**
         * Load initial list data.
         * <p>
         * This method is called to load the initial page(s) from the DataSource.
         * <p>
         * Result list must be a multiple of pageSize to enable efficient tiling.
         *
         * @param params   Parameters for initial load, including requested start position, load size, and
         *                 page size.
         * @param callback Callback that receives initial load data, including
         */
        @Override
        public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<MyItem> callback) {
            mPageSize = params.pageSize;
            int start = computeInitialLoadPosition(params,mTotalCount);
            int size = computeInitialLoadSize(params, start, mTotalCount);
            final List<MyItem> data = loadItems(start, size);
            callback.onResult(
                    data,
                    start,
                    mTotalCount);
        }

        private List<MyItem> loadItems(int requestedStartPosition, int requestedLoadSize) {
            List<MyItem> list = new ArrayList<>(requestedLoadSize);
            for (int i = requestedStartPosition; i < mTotalCount && i < requestedLoadSize + requestedStartPosition; i++) {
                list.add(new MyItem("Item No:" + i));
            }
            return list;
        }

        /**
         * Called to load a range of data from the DataSource.
         * <p>
         * This method is called to load additional pages from the DataSource after the
         * LoadInitialCallback passed to dispatchLoadInitial has initialized a PagedList.
         * <p>
         * Unlike {@link #loadInitial(LoadInitialParams, LoadInitialCallback)}, this method must return
         * the number of items requested, at the position requested.
         *
         * @param params   Parameters for load, including start position and load size.
         * @param callback Callback that receives loaded data.
         */
        @Override
        public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<MyItem> callback) {
            final List<MyItem> data = loadItems(params.startPosition, params.loadSize);
            callback.onResult(
                    data
            );


        }

    }

    class MyDataSourceFactory extends DataSource.Factory<Integer, MyItem>{

        /**
         * Create a DataSource.
         * <p>
         * The DataSource should invalidate itself if the snapshot is no longer valid. If a
         * DataSource becomes invalid, the only way to query more data is to create a new DataSource
         * from the Factory.
         * <p>
         * {@link LivePagedListBuilder} for example will construct a new PagedList and DataSource
         * when the current DataSource is invalidated, and pass the new PagedList through the
         * {@code LiveData<PagedList>} to observers.
         *
         * @return the new DataSource.
         */
        @Override
        public DataSource<Integer, MyItem> create() {
            return new MyDataSource();
        }
    }

}
